package com.dawn.tarot.application.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.dawn.tarot.domain.exception.TarotException;
import com.dawn.tarot.domain.service.VerificationMailService;

@Service
public class VerificationCodeService {
    private static final Duration CODE_TTL = Duration.ofMinutes(10);
    private static final Duration SEND_INTERVAL = Duration.ofMinutes(1);
    private static final int MAX_ATTEMPTS = 5;
    private static final SecureRandom RANDOM = new SecureRandom();
    private final StringRedisTemplate redis;
    private final VerificationMailService mailService;

    public VerificationCodeService(StringRedisTemplate redis, VerificationMailService mailService) {
        this.redis = redis;
        this.mailService = mailService;
    }

    public void send(String email, VerificationCodePurpose purpose) {
        String rateKey = rateKey(email, purpose);
        if (!Boolean.TRUE.equals(redis.opsForValue().setIfAbsent(rateKey, "1", SEND_INTERVAL))) {
            throw TarotException.of("CODE_SEND_TOO_FREQUENT", "验证码已发送，请 1 分钟后再试");
        }
        String code = String.format("%06d", RANDOM.nextInt(1_000_000));
        try {
            redis.opsForValue().set(codeKey(email, purpose), sha256(code), CODE_TTL);
            redis.delete(attemptKey(email, purpose));
            if (purpose == VerificationCodePurpose.REGISTER) {
                mailService.sendRegistrationCode(email, code);
            } else {
                mailService.sendPasswordResetCode(email, code);
            }
        } catch (RuntimeException e) {
            redis.delete(rateKey);
            redis.delete(codeKey(email, purpose));
            throw e;
        }
    }

    public void verify(String email, String code, VerificationCodePurpose purpose) {
        String expected = redis.opsForValue().get(codeKey(email, purpose));
        if (expected == null) {
            throw TarotException.of("CODE_EXPIRED", "验证码已过期或不存在");
        }
        Long attempts = redis.opsForValue().increment(attemptKey(email, purpose));
        if (attempts != null && attempts == 1) {
            redis.expire(attemptKey(email, purpose), CODE_TTL);
        }
        if (attempts != null && attempts > MAX_ATTEMPTS) {
            clear(email, purpose);
            throw TarotException.of("CODE_TOO_MANY_ATTEMPTS", "验证码错误次数过多，请重新获取");
        }
        if (!MessageDigest.isEqual(expected.getBytes(), sha256(code).getBytes())) {
            throw TarotException.of("INVALID_CODE", "验证码错误");
        }
        clear(email, purpose);
    }

    private void clear(String email, VerificationCodePurpose purpose) {
        redis.delete(codeKey(email, purpose));
        redis.delete(attemptKey(email, purpose));
    }
    private String codeKey(String email, VerificationCodePurpose purpose) { return "auth:code:" + purpose + ":" + email; }
    private String attemptKey(String email, VerificationCodePurpose purpose) { return "auth:code:attempt:" + purpose + ":" + email; }
    private String rateKey(String email, VerificationCodePurpose purpose) { return "auth:code:rate:" + purpose + ":" + email; }
    private String sha256(String value) {
        try {
            return java.util.HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) { throw new IllegalStateException(e); }
    }
}
