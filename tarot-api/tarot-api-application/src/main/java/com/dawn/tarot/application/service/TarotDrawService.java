package com.dawn.tarot.application.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dawn.tarot.domain.exception.TarotException;
import com.dawn.tarot.domain.model.Card;
import com.dawn.tarot.domain.model.DrawnCard;
import com.dawn.tarot.domain.model.TarotSession;
import com.dawn.tarot.domain.repository.CardRepository;
import com.dawn.tarot.domain.repository.SessionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 逐张抽卡:后端随机抽牌,不调用 LLM。
 */
@Slf4j
@Service
public class TarotDrawService {

    private static final double REVERSED_PROBABILITY = 0.3;

    private final SessionRepository sessionRepository;
    private final CardRepository cardRepository;

    public TarotDrawService(SessionRepository sessionRepository, CardRepository cardRepository) {
        this.sessionRepository = sessionRepository;
        this.cardRepository = cardRepository;
    }

    public DrawResult draw(String sessionId) {
        TarotSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new TarotException("SESSION_NOT_FOUND", "占卜会话不存在或已过期"));

        int needed = session.getSpread().getCardCount();
        if (session.isFullyDrawn()) {
            throw new TarotException("ALREADY_FULL", "本次占卜已抽满卡牌");
        }

        Card picked = pickUnusedCard(session);
        boolean reversed = ThreadLocalRandom.current().nextDouble() < REVERSED_PROBABILITY;
        int positionIndex = session.getDrawnCards().size();

        DrawnCard drawnCard = DrawnCard.builder()
                .id(picked.getId())
                .name(picked.getName())
                .image(picked.getImage())
                .reversed(reversed)
                .positionIndex(positionIndex)
                .build();

        session.getDrawnCards().add(drawnCard);
        sessionRepository.save(session);

        boolean complete = session.getDrawnCards().size() >= needed;
        log.info("[抽牌] sessionId={}, #{} {}({}), 进度={}/{}",
                sessionId, positionIndex + 1, picked.getName(),
                reversed ? "逆位" : "正位",
                session.getDrawnCards().size(), needed);
        return new DrawResult(drawnCard, session.getDrawnCards().size(), needed, complete);
    }

    /** 从未抽过的牌中随机取一张 */
    private Card pickUnusedCard(TarotSession session) {
        List<Card> all = cardRepository.findAll();
        if (all.isEmpty()) {
            throw new TarotException("NO_CARD", "卡牌数据未初始化");
        }
        Set<Integer> used = session.getDrawnCards().stream()
                .map(DrawnCard::getId).collect(Collectors.toCollection(HashSet::new));
        List<Card> available = all.stream()
                .filter(c -> !used.contains(c.getId()))
                .toList();
        if (available.isEmpty()) {
            throw new TarotException("NO_CARD", "无可用卡牌");
        }
        return available.get(ThreadLocalRandom.current().nextInt(available.size()));
    }
}
