package com.dawn.tarot.infrastructure.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dawn.tarot.domain.model.UserSession;

@Mapper
public interface UserSessionMapper {
    int insert(UserSession session);
    UserSession selectByTokenId(String tokenId);
    int revoke(String tokenId);
    int revokeByUserId(Long userId);
    int touch(String tokenId);
}
