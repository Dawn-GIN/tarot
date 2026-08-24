package com.dawn.tarot.domain.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenPayload {
    private Long userId;
    private String email;
    private String tokenId;
    private String type;
    private Instant expiresAt;
}
