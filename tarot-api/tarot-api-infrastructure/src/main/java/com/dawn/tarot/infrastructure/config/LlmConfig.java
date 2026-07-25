package com.dawn.tarot.infrastructure.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.dawn.tarot.infrastructure.llm.LlmProperties;

@Configuration
@EnableConfigurationProperties(LlmProperties.class)
public class LlmConfig {
}
