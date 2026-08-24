package com.dawn.tarot.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {
    private Long id;
    private Long userId;
    private String tokenId;
    private String refreshTokenHash;
    private LocalDateTime expiresAt;
    private LocalDateTime revokedAt;
    private LocalDateTime createdAt;
    private LocalDateTime lastUsedAt;
}
