package com.dawn.tarot.infrastructure.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dawn.tarot.domain.model.Card;

@Mapper
public interface CardMapper {
    List<Card> selectAll();
}
