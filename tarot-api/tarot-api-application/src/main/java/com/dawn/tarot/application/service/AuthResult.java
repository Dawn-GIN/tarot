package com.dawn.tarot.application.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResult {
    private Long userId;
    private String email;
    private String accessToken;
    private String refreshToken;
}
