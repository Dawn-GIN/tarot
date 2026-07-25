package com.dawn.tarot.infrastructure.llm;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.dawn.tarot.domain.exception.TarotException;
import com.dawn.tarot.domain.llm.LlmClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * OpenAI 兼容的 LLM 客户端(适配智谱 GLM 的 /chat/completions 接口)。
 */
@Slf4j
@Component
public class OpenAiCompatibleLlmClient implements LlmClient {

    private final WebClient webClient;
    private final LlmProperties props;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenAiCompatibleLlmClient(LlmProperties props) {
        this.props = props;
        this.webClient = WebClient.builder()
                .baseUrl(props.getBaseUrl())
                .defaultHeader("Authorization", "Bearer " + props.getApiKey())
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    private Map<String, Object> buildBody(String systemPrompt, String userPrompt, boolean stream) {
        return Map.of(
                "model", props.getModel(),
                "stream", stream,
                "messages", List.of(
                        Map.of("role", "system", "content", systemPrompt),
                        Map.of("role", "user", "content", userPrompt)
                )
        );
    }

    @Override
    public String complete(String systemPrompt, String userPrompt) {
        ensureApiKey();
        try {
            String resp = webClient.post()
                    .uri("/chat/completions")
                    .bodyValue(buildBody(systemPrompt, userPrompt, false))
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(props.getTimeoutSeconds()))
                    .block();
            JsonNode root = objectMapper.readTree(resp);
            return root.path("choices").path(0).path("message").path("content").asText();
        } catch (TarotException e) {
            throw e;
        } catch (Exception e) {
            log.error("LLM complete 调用失败", e);
            throw new TarotException("LLM_ERROR", "LLM 调用失败: " + e.getMessage());
        }
    }

    @Override
    public void stream(String systemPrompt, String userPrompt,
                       Consumer<String> onChunk,
                       Consumer<Throwable> onError,
                       Runnable onComplete) {
        ensureApiKey();
        Flux<String> flux = webClient.post()
                .uri("/chat/completions")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .bodyValue(buildBody(systemPrompt, userPrompt, true))
                .retrieve()
                .bodyToFlux(String.class)
                .timeout(Duration.ofSeconds(props.getTimeoutSeconds()));

        flux.subscribe(
                data -> handleStreamData(data, onChunk),
                onError,
                onComplete
        );
    }

    /** 解析 SSE data 行,提取增量 content */
    private void handleStreamData(String data, Consumer<String> onChunk) {
        if (data == null || data.isBlank() || "[DONE]".equals(data.trim())) {
            return;
        }
        try {
            JsonNode root = objectMapper.readTree(data);
            String delta = root.path("choices").path(0).path("delta").path("content").asText("");
            if (!delta.isEmpty()) {
                onChunk.accept(delta);
            }
        } catch (Exception e) {
            log.warn("解析流式响应片段失败: {}", data, e);
        }
    }

    private void ensureApiKey() {
        if (props.getApiKey() == null || props.getApiKey().isBlank()) {
            throw new TarotException("LLM_NO_KEY", "未配置 LLM_API_KEY 环境变量");
        }
    }
}
