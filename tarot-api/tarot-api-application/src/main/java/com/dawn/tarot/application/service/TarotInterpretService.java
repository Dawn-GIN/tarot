package com.dawn.tarot.application.service;

import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.dawn.tarot.application.prompt.PromptBuilder;
import com.dawn.tarot.domain.exception.TarotException;
import com.dawn.tarot.domain.llm.LlmClient;
import com.dawn.tarot.domain.model.TarotSession;
import com.dawn.tarot.domain.repository.SessionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 解牌:第二次 LLM 调用,流式输出。框架无关,通过回调向上层推送。
 */
@Slf4j
@Service
public class TarotInterpretService {

    private final SessionRepository sessionRepository;
    private final LlmClient llmClient;
    private final PromptBuilder promptBuilder;

    public TarotInterpretService(SessionRepository sessionRepository, LlmClient llmClient,
                                 PromptBuilder promptBuilder) {
        this.sessionRepository = sessionRepository;
        this.llmClient = llmClient;
        this.promptBuilder = promptBuilder;
    }

    /**
     * 流式解读。
     *
     * @param onChunk    每个增量文本片段回调
     * @param onError    出错回调
     * @param onComplete 完成回调(参数为完整解读文本)
     */
    public void interpret(Long userId, String sessionId,
                          Consumer<String> onChunk,
                          Consumer<Throwable> onError,
                          Consumer<String> onComplete) {
        TarotSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new TarotException("SESSION_NOT_FOUND", "占卜会话不存在或已过期"));
        if (!userId.equals(session.getUserId())) {
            throw TarotException.of("SESSION_FORBIDDEN", "无权操作该占卜会话");
        }
        if (!session.isFullyDrawn()) {
            throw new TarotException("NOT_FULLY_DRAWN", "尚未抽满卡牌,无法解读");
        }

        String systemPrompt = promptBuilder.interpretSystemPrompt();
        String userPrompt = promptBuilder.interpretUserPrompt(
                session.getQuestion(), session.getSpread(), session.getDrawnCards());

        log.info("[解读] LLM流式调用开始, sessionId={}, 牌数={}", sessionId, session.getDrawnCards().size());
        StringBuilder full = new StringBuilder();
        llmClient.stream(systemPrompt, userPrompt,
                chunk -> {
                    full.append(chunk);
                    onChunk.accept(chunk);
                },
                onError,
                () -> {
                    log.info("[解读] LLM流式调用完成, sessionId={}, 输出字数={}", sessionId, full.length());
                    session.setInterpretation(full.toString());
                    sessionRepository.save(session);
                    onComplete.accept(full.toString());
                });
    }
}
