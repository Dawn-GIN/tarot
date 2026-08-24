package com.dawn.tarot.interfaces.dto;

import com.dawn.tarot.application.service.AuthResult;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Long userId; private String email; private String accessToken; private String refreshToken;
    public static AuthResponse from(AuthResult result) { return new AuthResponse(result.getUserId(), result.getEmail(), result.getAccessToken(), result.getRefreshToken()); }
}
