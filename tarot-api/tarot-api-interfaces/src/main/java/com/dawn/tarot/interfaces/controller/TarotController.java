package com.dawn.tarot.interfaces.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.dawn.tarot.application.service.DrawResult;
import com.dawn.tarot.application.service.TarotDrawService;
import com.dawn.tarot.application.service.TarotInterpretService;
import com.dawn.tarot.application.service.TarotStartService;
import com.dawn.tarot.domain.model.TarotSession;
import com.dawn.tarot.interfaces.dto.ApiResponse;
import com.dawn.tarot.interfaces.dto.DrawRequest;
import com.dawn.tarot.interfaces.dto.DrawResponse;
import com.dawn.tarot.interfaces.dto.StartRequest;
import com.dawn.tarot.interfaces.dto.StartResponse;
import com.dawn.tarot.interfaces.support.UserContext;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/tarot")
public class TarotController {

    private final TarotStartService startService;
    private final TarotDrawService drawService;
    private final TarotInterpretService interpretService;

    public TarotController(TarotStartService startService, TarotDrawService drawService,
                           TarotInterpretService interpretService) {
        this.startService = startService;
        this.drawService = drawService;
        this.interpretService = interpretService;
    }

    @PostMapping("/start")
    public ApiResponse<StartResponse> start(@Valid @RequestBody StartRequest request) {
        TarotSession session = startService.start(UserContext.currentUserId(), request.getQuestion());
        return ApiResponse.ok(new StartResponse(session.getSessionId(), session.getSpread()));
    }

    @PostMapping("/draw")
    public ApiResponse<DrawResponse> draw(@Valid @RequestBody DrawRequest request) {
        DrawResult result = drawService.draw(request.getSessionId());
        return ApiResponse.ok(DrawResponse.from(result));
    }

    @GetMapping(value = "/interpret", produces = "text/event-stream;charset=UTF-8")
    public SseEmitter interpret(@RequestParam String sessionId) {
        SseEmitter emitter = new SseEmitter(120_000L);
        CompletableFuture.runAsync(() -> interpretService.interpret(
                sessionId,
                chunk -> sendChunk(emitter, chunk),
                error -> completeWithError(emitter, error),
                full -> completeStream(emitter)
        ));
        return emitter;
    }

    private void sendChunk(SseEmitter emitter, String chunk) {
        try {
            emitter.send(SseEmitter.event()
                    .name("message")
                    .data(chunk, new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)));
        } catch (IOException e) {
            log.warn("SSE 发送失败", e);
            emitter.completeWithError(e);
        }
    }

    private void completeStream(SseEmitter emitter) {
        try {
            emitter.send(SseEmitter.event().name("done").data("[DONE]"));
        } catch (IOException e) {
            log.warn("SSE done 事件发送失败", e);
        }
        emitter.complete();
    }

    private void completeWithError(SseEmitter emitter, Throwable error) {
        log.error("解读流式输出异常", error);
        try {
            String msg = error.getMessage() == null ? "解读失败" : error.getMessage();
            emitter.send(SseEmitter.event().name("error")
                    .data(new String(msg.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8)));
        } catch (IOException e) {
            log.warn("SSE error 事件发送失败", e);
        }
        emitter.complete();
    }
}
