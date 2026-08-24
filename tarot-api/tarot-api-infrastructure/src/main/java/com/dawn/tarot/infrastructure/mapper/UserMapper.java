package com.dawn.tarot.infrastructure.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dawn.tarot.domain.model.TarotUser;

@Mapper
public interface UserMapper {
    TarotUser selectByEmail(String email);
    TarotUser selectById(Long id);
    int insert(TarotUser user);
    int updatePassword(Long id, String passwordHash);
    int updateLastLogin(Long id);
}
