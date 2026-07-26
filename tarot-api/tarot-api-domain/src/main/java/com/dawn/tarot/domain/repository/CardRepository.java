package com.dawn.tarot.domain.repository;

import java.util.List;
import java.util.Optional;

import com.dawn.tarot.domain.model.Card;

public interface CardRepository {

    /** 全部 78 张卡牌 */
    List<Card> findAll();

    /** 按 id 查询单张卡牌 */
    Optional<Card> findById(Integer id);
}
