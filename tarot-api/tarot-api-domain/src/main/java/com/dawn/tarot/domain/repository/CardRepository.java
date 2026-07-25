package com.dawn.tarot.domain.repository;

import java.util.List;

import com.dawn.tarot.domain.model.Card;

public interface CardRepository {

    /** 全部 78 张卡牌 */
    List<Card> findAll();
}
