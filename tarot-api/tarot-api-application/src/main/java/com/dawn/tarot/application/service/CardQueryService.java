package com.dawn.tarot.application.service;

import org.springframework.stereotype.Service;

import com.dawn.tarot.domain.exception.TarotException;
import com.dawn.tarot.domain.model.Card;
import com.dawn.tarot.domain.repository.CardRepository;

/**
 * 卡牌查询服务。
 */
@Service
public class CardQueryService {

    private final CardRepository cardRepository;

    public CardQueryService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card getById(Integer id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new TarotException("CARD_NOT_FOUND", "卡牌不存在: " + id));
    }
}
