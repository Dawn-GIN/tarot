package com.dawn.tarot.application.chain.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dawn.tarot.application.chain.StartContext;
import com.dawn.tarot.application.chain.StartHandler;
import com.dawn.tarot.application.prompt.PromptBuilder;
import com.dawn.tarot.domain.exception.TarotException;
import com.dawn.tarot.domain.llm.LlmClient;
import com.dawn.tarot.domain.model.Spread;
import com.dawn.tarot.domain.repository.SpreadRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 调用 LLM 选牌型,解析结构化 JSON。LLM 失败时回退到默认牌型。
 */
@Slf4j
@Component
public class SelectSpreadHandler implements StartHandler {

    private final SpreadRepository spreadRepository;
    private final LlmClient llmClient;
    private final PromptBuilder promptBuilder;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SelectSpreadHandler(SpreadRepository spreadRepository, LlmClient llmClient,
                               PromptBuilder promptBuilder) {
        this.spreadRepository = spreadRepository;
        this.llmClient = llmClient;
        this.promptBuilder = promptBuilder;
    }

    @Override
    public void handle(StartContext context) {
        List<Spread> spreads = spreadRepository.findAll();
        if (spreads.isEmpty()) {
            throw new TarotException("NO_SPREAD", "系统未配置任何牌型");
        }
        String code = askLlmForSpreadCode(context.getQuestion(), spreads);
        Spread chosen = spreadRepository.findByCode(code)
                .orElseGet(() -> spreads.get(0));
        log.info("[选牌阵] 结果={}, code={}", chosen.getName(), chosen.getCode());
        context.setSpread(chosen);
    }

    private String askLlmForSpreadCode(String question, List<Spread> spreads) {
        try {
            String content = llmClient.complete(
                    promptBuilder.spreadSystemPrompt(spreads),
                    promptBuilder.spreadUserPrompt(question));
            String json = extractJson(content);
            JsonNode node = objectMapper.readTree(json);
            String code = node.path("spreadCode").asText("");
            if (code.isBlank()) {
                throw new IllegalStateException("spreadCode 为空");
            }
            return code;
        } catch (Exception e) {
            log.warn("LLM 选牌型失败,回退默认牌型: {}", e.getMessage());
            return spreads.get(0).getCode();
        }
    }

    /** 从可能带 markdown 代码块的文本里提取 JSON 主体 */
    private String extractJson(String content) {
        if (content == null) {
            return "{}";
        }
        int start = content.indexOf('{');
        int end = content.lastIndexOf('}');
        if (start >= 0 && end > start) {
            return content.substring(start, end + 1);
        }
        return content;
    }

    @Override
    public int getOrder() {
        return 30;
    }
}
