package com.dawn.tarot.infrastructure.auth;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.dawn.tarot.domain.exception.TarotException;
import com.dawn.tarot.domain.model.TokenPayload;
import com.dawn.tarot.domain.service.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtTokenService implements TokenService {
    private static final String TYPE_ACCESS = "access";
    private static final String TYPE_REFRESH = "refresh";
    private final JwtProperties properties;

    public JwtTokenService(JwtProperties properties) { this.properties = properties; }

    @Override
    public String createAccessToken(Long userId, String email) {
        return create(userId, email, null, TYPE_ACCESS, properties.getAccessTokenMinutes(), ChronoUnit.MINUTES);
    }

    @Override
    public String createRefreshToken(Long userId, String email, String tokenId) {
        return create(userId, email, tokenId, TYPE_REFRESH, properties.getRefreshTokenDays(), ChronoUnit.DAYS);
    }

    @Override
    public TokenPayload parse(String token) {
        try {
            Claims claims = Jwts.parser().verifyWith((javax.crypto.SecretKey) signingKey()).build()
                    .parseSignedClaims(token).getPayload();
            return new TokenPayload(Long.parseLong(claims.getSubject()), claims.get("email", String.class),
                    claims.getId(), claims.get("type", String.class), claims.getExpiration().toInstant());
        } catch (Exception e) {
            log.debug("JWT 校验失败", e);
            throw TarotException.of("INVALID_TOKEN", "登录状态无效或已过期");
        }
    }

    private String create(Long userId, String email, String tokenId, String type, long amount, ChronoUnit unit) {
        Instant now = Instant.now();
        Instant expiresAt = now.plus(amount, unit);
        var builder = Jwts.builder().subject(String.valueOf(userId)).issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt)).claim("email", email).claim("type", type).signWith(signingKey());
        if (tokenId != null) builder.id(tokenId);
        return builder.compact();
    }

    private Key signingKey() {
        if (properties.getJwtSecret() == null || properties.getJwtSecret().isBlank()) {
            throw TarotException.of("AUTH_NOT_CONFIGURED", "登录服务尚未配置");
        }
        try {
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.getJwtSecret()));
        } catch (IllegalArgumentException e) {
            throw TarotException.of("AUTH_NOT_CONFIGURED", "登录服务密钥配置无效");
        }
    }
}
