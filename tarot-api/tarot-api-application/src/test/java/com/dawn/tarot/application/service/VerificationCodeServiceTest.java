package com.dawn.tarot.application.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.dawn.tarot.domain.exception.TarotException;
import com.dawn.tarot.domain.service.VerificationMailService;

@ExtendWith(MockitoExtension.class)
class VerificationCodeServiceTest {
    @Mock private StringRedisTemplate redis;
    @Mock private ValueOperations<String, String> values;
    @Mock private VerificationMailService mailService;
    private VerificationCodeService service;

    @BeforeEach
    void setUp() {
        when(redis.opsForValue()).thenReturn(values);
        service = new VerificationCodeService(redis, mailService);
    }

    @Test
    void sendRejectsRequestsInsideOneMinuteInterval() {
        when(values.setIfAbsent(anyString(), anyString(), any())).thenReturn(false);

        TarotException error = assertThrows(TarotException.class,
                () -> service.send("user@example.com", VerificationCodePurpose.REGISTER));

        assertEquals("CODE_SEND_TOO_FREQUENT", error.getCode());
        verify(mailService, never()).sendRegistrationCode(anyString(), anyString());
    }

    @Test
    void verifyAcceptsCorrectCodeAndClearsIt() throws Exception {
        String codeKey = "auth:code:REGISTER:user@example.com";
        when(values.get(codeKey)).thenReturn(sha256("123456"));
        when(values.increment("auth:code:attempt:REGISTER:user@example.com")).thenReturn(1L);

        assertDoesNotThrow(() -> service.verify("user@example.com", "123456", VerificationCodePurpose.REGISTER));

        verify(redis).delete(codeKey);
        verify(redis).delete("auth:code:attempt:REGISTER:user@example.com");
    }

    @Test
    void verifyRejectsAfterTooManyAttempts() throws Exception {
        when(values.get("auth:code:REGISTER:user@example.com")).thenReturn(sha256("123456"));
        when(values.increment("auth:code:attempt:REGISTER:user@example.com")).thenReturn(6L);

        TarotException error = assertThrows(TarotException.class,
                () -> service.verify("user@example.com", "123456", VerificationCodePurpose.REGISTER));

        assertEquals("CODE_TOO_MANY_ATTEMPTS", error.getCode());
    }

    private String sha256(String value) throws Exception {
        return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8)));
    }
}
