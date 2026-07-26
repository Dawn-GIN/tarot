package com.dawn.tarot.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dawn.tarot.domain.model.Card;
import com.dawn.tarot.domain.repository.CardRepository;
import com.dawn.tarot.infrastructure.mapper.CardMapper;

@Repository
public class CardRepositoryImpl implements CardRepository {

    private final CardMapper cardMapper;

    public CardRepositoryImpl(CardMapper cardMapper) {
        this.cardMapper = cardMapper;
    }

    @Override
    public List<Card> findAll() {
        return cardMapper.selectAll();
    }

    @Override
    public Optional<Card> findById(Integer id) {
        return Optional.ofNullable(cardMapper.selectById(id));
    }
}
