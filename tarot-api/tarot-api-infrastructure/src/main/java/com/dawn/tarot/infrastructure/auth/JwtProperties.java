package com.dawn.tarot.infrastructure.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "tarot.auth")
public class JwtProperties {
    /** Base64 编码的至少 256 位 HMAC 密钥。 */
    private String jwtSecret = "";
    private long accessTokenMinutes = 30;
    private long refreshTokenDays = 30;
}
