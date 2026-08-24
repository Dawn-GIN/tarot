package com.dawn.tarot.application.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HexFormat;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dawn.tarot.domain.exception.TarotException;
import com.dawn.tarot.domain.model.TarotUser;
import com.dawn.tarot.domain.model.TokenPayload;
import com.dawn.tarot.domain.model.UserSession;
import com.dawn.tarot.domain.repository.UserRepository;
import com.dawn.tarot.domain.repository.UserSessionRepository;
import com.dawn.tarot.domain.service.TokenService;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserSessionRepository sessionRepository;
    private final VerificationCodeService codeService;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, UserSessionRepository sessionRepository,
                       VerificationCodeService codeService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.codeService = codeService;
        this.tokenService = tokenService;
    }

    public void sendCode(String rawEmail, VerificationCodePurpose purpose) {
        String email = normalizeEmail(rawEmail);
        if (purpose == VerificationCodePurpose.REGISTER && userRepository.findByEmail(email).isPresent()) {
            throw TarotException.of("EMAIL_ALREADY_REGISTERED", "该邮箱已注册，请直接登录");
        }
        // 找不到待重置账号时同样返回成功，避免通过该接口枚举已注册邮箱。
        if (purpose == VerificationCodePurpose.RESET_PASSWORD && userRepository.findByEmail(email).isEmpty()) return;
        codeService.send(email, purpose);
    }

    public AuthResult register(String rawEmail, String password, String code) {
        String email = normalizeEmail(rawEmail);
        validatePassword(password);
        if (userRepository.findByEmail(email).isPresent()) {
            throw TarotException.of("EMAIL_ALREADY_REGISTERED", "该邮箱已注册，请直接登录");
        }
        codeService.verify(email, code, VerificationCodePurpose.REGISTER);
        TarotUser user = TarotUser.builder().email(email).passwordHash(passwordEncoder.encode(password)).status(1).build();
        userRepository.save(user);
        return createSession(user);
    }

    public AuthResult login(String rawEmail, String password) {
        String email = normalizeEmail(rawEmail);
        TarotUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> TarotException.of("INVALID_CREDENTIALS", "邮箱或密码错误"));
        if (!Integer.valueOf(1).equals(user.getStatus())) throw TarotException.of("ACCOUNT_DISABLED", "账号已被禁用");
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw TarotException.of("INVALID_CREDENTIALS", "邮箱或密码错误");
        }
        userRepository.updateLastLogin(user.getId());
        return createSession(user);
    }

    public void resetPassword(String rawEmail, String password, String code) {
        String email = normalizeEmail(rawEmail);
        validatePassword(password);
        TarotUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> TarotException.of("INVALID_CODE", "验证码错误或已过期"));
        codeService.verify(email, code, VerificationCodePurpose.RESET_PASSWORD);
        userRepository.updatePassword(user.getId(), passwordEncoder.encode(password));
        sessionRepository.revokeByUserId(user.getId());
    }

    public AuthResult refresh(String refreshToken) {
        TokenPayload payload = tokenService.parse(refreshToken);
        if (!"refresh".equals(payload.getType()) || payload.getTokenId() == null) throw TarotException.of("INVALID_TOKEN", "刷新令牌无效");
        UserSession session = sessionRepository.findByTokenId(payload.getTokenId())
                .orElseThrow(() -> TarotException.of("INVALID_TOKEN", "登录会话不存在"));
        if (session.getRevokedAt() != null || session.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw TarotException.of("INVALID_TOKEN", "登录会话已失效");
        }
        if (!MessageDigest.isEqual(session.getRefreshTokenHash().getBytes(StandardCharsets.UTF_8), hash(refreshToken).getBytes(StandardCharsets.UTF_8))) {
            throw TarotException.of("INVALID_TOKEN", "刷新令牌无效");
        }
        TarotUser user = userRepository.findById(payload.getUserId())
                .orElseThrow(() -> TarotException.of("INVALID_TOKEN", "用户不存在"));
        if (!Integer.valueOf(1).equals(user.getStatus())) throw TarotException.of("ACCOUNT_DISABLED", "账号已被禁用");
        sessionRepository.revoke(payload.getTokenId());
        return createSession(user);
    }

    public void logout(String refreshToken) {
        TokenPayload payload = tokenService.parse(refreshToken);
        if ("refresh".equals(payload.getType()) && payload.getTokenId() != null) sessionRepository.revoke(payload.getTokenId());
    }

    private AuthResult createSession(TarotUser user) {
        String tokenId = UUID.randomUUID().toString();
        String refreshToken = tokenService.createRefreshToken(user.getId(), user.getEmail(), tokenId);
        TokenPayload payload = tokenService.parse(refreshToken);
        sessionRepository.save(UserSession.builder().userId(user.getId()).tokenId(tokenId)
                .refreshTokenHash(hash(refreshToken)).expiresAt(LocalDateTime.ofInstant(payload.getExpiresAt(), ZoneId.systemDefault())).build());
        return new AuthResult(user.getId(), user.getEmail(), tokenService.createAccessToken(user.getId(), user.getEmail()), refreshToken);
    }

    private String normalizeEmail(String email) { return email == null ? "" : email.trim().toLowerCase(java.util.Locale.ROOT); }
    private void validatePassword(String password) {
        if (password == null || password.length() < 8 || password.length() > 72) throw TarotException.of("WEAK_PASSWORD", "密码长度需为 8 至 72 位");
    }
    private String hash(String value) {
        try { return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8))); }
        catch (NoSuchAlgorithmException e) { throw new IllegalStateException(e); }
    }
}
