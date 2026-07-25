package com.dawn.tarot.domain.repository;

import java.util.Optional;

import com.dawn.tarot.domain.model.TarotSession;

/**
 * 占卜会话存储(实现基于 Redis)。
 */
public interface SessionRepository {

    void save(TarotSession session);

    Optional<TarotSession> findById(String sessionId);
}
