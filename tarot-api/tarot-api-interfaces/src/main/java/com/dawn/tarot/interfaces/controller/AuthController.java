package com.dawn.tarot.interfaces.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dawn.tarot.application.service.AuthService;
import com.dawn.tarot.interfaces.dto.*;
import com.dawn.tarot.interfaces.support.UserContext;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }
    @PostMapping("/send-code") public ApiResponse<Void> sendCode(@Valid @RequestBody SendCodeRequest request) { authService.sendCode(request.getEmail(), request.getPurpose()); return ApiResponse.ok(null); }
    @PostMapping("/register") public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest request) { return ApiResponse.ok(AuthResponse.from(authService.register(request.getEmail(), request.getPassword(), request.getCode()))); }
    @PostMapping("/login") public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) { return ApiResponse.ok(AuthResponse.from(authService.login(request.getEmail(), request.getPassword()))); }
    @PostMapping("/reset-password") public ApiResponse<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) { authService.resetPassword(request.getEmail(), request.getPassword(), request.getCode()); return ApiResponse.ok(null); }
    @PostMapping("/refresh") public ApiResponse<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) { return ApiResponse.ok(AuthResponse.from(authService.refresh(request.getRefreshToken()))); }
    @PostMapping("/logout") public ApiResponse<Void> logout(@Valid @RequestBody RefreshTokenRequest request) { authService.logout(request.getRefreshToken()); return ApiResponse.ok(null); }
    @GetMapping("/me") public ApiResponse<Map<String, Long>> me() { return ApiResponse.ok(Map.of("userId", UserContext.currentUserId())); }
}
