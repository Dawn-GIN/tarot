package com.dawn.tarot.infrastructure.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.dawn.tarot.infrastructure.mail.MailProperties;

@Configuration
@EnableConfigurationProperties(MailProperties.class)
public class MailConfig {
}
