package com.dawn.tarot.domain.repository;

import java.util.Optional;

import com.dawn.tarot.domain.model.TarotUser;

public interface UserRepository {
    Optional<TarotUser> findByEmail(String email);
    Optional<TarotUser> findById(Long id);
    void save(TarotUser user);
    void updatePassword(Long userId, String passwordHash);
    void updateLastLogin(Long userId);
}
