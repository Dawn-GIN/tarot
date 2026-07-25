package com.dawn.tarot.infrastructure.llm;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "tarot.llm")
public class LlmProperties {
    /** OpenAI 兼容 base-url */
    private String baseUrl = "https://dashscope.aliyuncs.com/compatible-mode/v1";
    private String apiKey = "";
    private String model = "qwen-plus";
    private int timeoutSeconds = 60;
}
