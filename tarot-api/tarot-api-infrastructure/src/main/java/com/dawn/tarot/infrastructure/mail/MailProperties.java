package com.dawn.tarot.infrastructure.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "tarot.mail")
public class MailProperties {
    /** 用于发信的已验证邮箱地址。 */
    private String from = "";
}
