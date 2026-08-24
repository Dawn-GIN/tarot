package com.dawn.tarot.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Base64;

import org.junit.jupiter.api.Test;

import com.dawn.tarot.domain.exception.TarotException;
import com.dawn.tarot.domain.model.TokenPayload;
import com.dawn.tarot.infrastructure.auth.JwtProperties;
import com.dawn.tarot.infrastructure.auth.JwtTokenService;

class JwtTokenServiceTest {
    @Test
    void accessAndRefreshTokensPreserveTheirDistinctTypes() {
        JwtTokenService service = tokenService();

        TokenPayload access = service.parse(service.createAccessToken(9L, "user@example.com"));
        TokenPayload refresh = service.parse(service.createRefreshToken(9L, "user@example.com", "session-1"));

        assertEquals("access", access.getType());
        assertEquals("refresh", refresh.getType());
        assertEquals("session-1", refresh.getTokenId());
    }

    @Test
    void invalidTokenIsRejected() {
        TarotException error = assertThrows(TarotException.class, () -> tokenService().parse("not-a-token"));
        assertEquals("INVALID_TOKEN", error.getCode());
    }

    private JwtTokenService tokenService() {
        JwtProperties properties = new JwtProperties();
        properties.setJwtSecret(Base64.getEncoder().encodeToString(new byte[32]));
        return new JwtTokenService(properties);
    }
}
