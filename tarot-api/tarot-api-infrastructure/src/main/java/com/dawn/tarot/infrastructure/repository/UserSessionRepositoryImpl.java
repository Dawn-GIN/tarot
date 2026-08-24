package com.dawn.tarot.infrastructure.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dawn.tarot.domain.model.UserSession;
import com.dawn.tarot.domain.repository.UserSessionRepository;
import com.dawn.tarot.infrastructure.mapper.UserSessionMapper;

@Repository
public class UserSessionRepositoryImpl implements UserSessionRepository {
    private final UserSessionMapper mapper;
    public UserSessionRepositoryImpl(UserSessionMapper mapper) { this.mapper = mapper; }
    public void save(UserSession session) { mapper.insert(session); }
    public Optional<UserSession> findByTokenId(String tokenId) { return Optional.ofNullable(mapper.selectByTokenId(tokenId)); }
    public void revoke(String tokenId) { mapper.revoke(tokenId); }
    public void revokeByUserId(Long userId) { mapper.revokeByUserId(userId); }
    public void touch(String tokenId) { mapper.touch(tokenId); }
}
