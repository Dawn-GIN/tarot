package com.dawn.tarot.domain.repository;

import java.util.Optional;

import com.dawn.tarot.domain.model.UserSession;

public interface UserSessionRepository {
    void save(UserSession session);
    Optional<UserSession> findByTokenId(String tokenId);
    void revoke(String tokenId);
    void revokeByUserId(Long userId);
    void touch(String tokenId);
}
