package com.dawn.tarot.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dawn.tarot.domain.exception.TarotException;
import com.dawn.tarot.domain.model.TarotUser;
import com.dawn.tarot.domain.model.TokenPayload;
import com.dawn.tarot.domain.model.UserSession;
import com.dawn.tarot.domain.repository.UserRepository;
import com.dawn.tarot.domain.repository.UserSessionRepository;
import com.dawn.tarot.domain.service.TokenService;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private UserSessionRepository sessionRepository;
    @Mock private VerificationCodeService codeService;
    @Mock private TokenService tokenService;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(userRepository, sessionRepository, codeService, tokenService);
    }

    @Test
    void registerCreatesUserAndIndependentLoginSession() {
        stubTokens();
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());
        doAnswer(invocation -> { invocation.getArgument(0, TarotUser.class).setId(7L); return null; }).when(userRepository).save(any());

        AuthResult result = authService.register(" User@Example.com ", "secure-password", "123456");

        assertEquals(7L, result.getUserId());
        assertEquals("user@example.com", result.getEmail());
        assertEquals("access-token", result.getAccessToken());
        verify(codeService).verify("user@example.com", "123456", VerificationCodePurpose.REGISTER);
        ArgumentCaptor<TarotUser> userCaptor = ArgumentCaptor.forClass(TarotUser.class);
        verify(userRepository).save(userCaptor.capture());
        assertEquals("user@example.com", userCaptor.getValue().getEmail());
        assertEquals(1, userCaptor.getValue().getStatus());
        ArgumentCaptor<UserSession> sessionCaptor = ArgumentCaptor.forClass(UserSession.class);
        verify(sessionRepository).save(sessionCaptor.capture());
        assertEquals(7L, sessionCaptor.getValue().getUserId());
        assertEquals(false, sessionCaptor.getValue().getTokenId().isBlank());
    }

    @Test
    void registerRejectsExistingEmailBeforeConsumingCode() {
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user()));

        TarotException error = assertThrows(TarotException.class,
                () -> authService.register("user@example.com", "secure-password", "123456"));

        assertEquals("EMAIL_ALREADY_REGISTERED", error.getCode());
        verify(codeService, never()).verify(anyString(), anyString(), any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void loginRejectsWrongPassword() {
        TarotUser user = user();
        user.setPasswordHash(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("right-password"));
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        TarotException error = assertThrows(TarotException.class,
                () -> authService.login("user@example.com", "wrong-password"));

        assertEquals("INVALID_CREDENTIALS", error.getCode());
        verify(sessionRepository, never()).save(any());
    }

    @Test
    void resetPasswordUpdatesPasswordAndRevokesAllDeviceSessions() {
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user()));

        authService.resetPassword("user@example.com", "new-secure-password", "654321");

        verify(codeService).verify("user@example.com", "654321", VerificationCodePurpose.RESET_PASSWORD);
        verify(userRepository).updatePassword(org.mockito.ArgumentMatchers.eq(7L), anyString());
        verify(sessionRepository).revokeByUserId(7L);
    }

    @Test
    void refreshRotatesExistingRefreshSession() throws Exception {
        stubTokens();
        TokenPayload oldPayload = new TokenPayload(7L, "user@example.com", "old-session", "refresh", Instant.now().plusSeconds(3600));
        when(tokenService.parse("old-refresh")).thenReturn(oldPayload);
        when(sessionRepository.findByTokenId("old-session")).thenReturn(Optional.of(UserSession.builder()
                .userId(7L).tokenId("old-session").refreshTokenHash(sha256("old-refresh"))
                .expiresAt(LocalDateTime.now().plusHours(1)).build()));
        when(userRepository.findById(7L)).thenReturn(Optional.of(user()));

        AuthResult result = authService.refresh("old-refresh");

        assertEquals("access-token", result.getAccessToken());
        verify(sessionRepository).revoke("old-session");
        verify(sessionRepository, times(1)).save(any(UserSession.class));
    }

    private TarotUser user() {
        return TarotUser.builder().id(7L).email("user@example.com").passwordHash("unused").status(1).build();
    }

    private void stubTokens() {
        when(tokenService.createRefreshToken(anyLong(), anyString(), anyString())).thenReturn("refresh-token");
        when(tokenService.createAccessToken(anyLong(), anyString())).thenReturn("access-token");
        when(tokenService.parse("refresh-token")).thenReturn(new TokenPayload(7L, "user@example.com", "new-session", "refresh", Instant.now().plusSeconds(3600)));
    }

    private String sha256(String value) throws Exception {
        return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8)));
    }
}
