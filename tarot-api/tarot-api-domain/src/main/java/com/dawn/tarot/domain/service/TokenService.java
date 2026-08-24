package com.dawn.tarot.domain.service;

import com.dawn.tarot.domain.model.TokenPayload;

public interface TokenService {
    String createAccessToken(Long userId, String email);
    String createRefreshToken(Long userId, String email, String tokenId);
    TokenPayload parse(String token);
}
