package com.dawn.tarot.infrastructure.repository;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.dawn.tarot.domain.model.TarotSession;
import com.dawn.tarot.domain.repository.SessionRepository;

@Repository
public class RedisSessionRepository implements SessionRepository {

    private static final String KEY_PREFIX = "tarot:session:";

    private final RedisTemplate<String, Object> redisTemplate;
    private final long ttlMinutes;

    public RedisSessionRepository(RedisTemplate<String, Object> redisTemplate,
                                  @Value("${tarot.session.ttl-minutes:30}") long ttlMinutes) {
        this.redisTemplate = redisTemplate;
        this.ttlMinutes = ttlMinutes;
    }

    @Override
    public void save(TarotSession session) {
        redisTemplate.opsForValue().set(
                KEY_PREFIX + session.getSessionId(),
                session,
                Duration.ofMinutes(ttlMinutes));
    }

    @Override
    public Optional<TarotSession> findById(String sessionId) {
        Object value = redisTemplate.opsForValue().get(KEY_PREFIX + sessionId);
        if (value instanceof TarotSession session) {
            return Optional.of(session);
        }
        return Optional.empty();
    }
}
