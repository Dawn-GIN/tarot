package com.dawn.tarot.infrastructure.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dawn.tarot.domain.model.TarotUser;
import com.dawn.tarot.domain.repository.UserRepository;
import com.dawn.tarot.infrastructure.mapper.UserMapper;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserMapper mapper;
    public UserRepositoryImpl(UserMapper mapper) { this.mapper = mapper; }
    public Optional<TarotUser> findByEmail(String email) { return Optional.ofNullable(mapper.selectByEmail(email)); }
    public Optional<TarotUser> findById(Long id) { return Optional.ofNullable(mapper.selectById(id)); }
    public void save(TarotUser user) { mapper.insert(user); }
    public void updatePassword(Long userId, String passwordHash) { mapper.updatePassword(userId, passwordHash); }
    public void updateLastLogin(Long userId) { mapper.updateLastLogin(userId); }
}
