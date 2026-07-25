package com.dawn.tarot.application.chain.handler;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.dawn.tarot.application.chain.StartContext;
import com.dawn.tarot.application.chain.StartHandler;
import com.dawn.tarot.domain.model.TarotSession;
import com.dawn.tarot.domain.repository.SessionRepository;

/**
 * 创建会话并落 Redis。
 */
@Component
public class CreateSessionHandler implements StartHandler {

    private final SessionRepository sessionRepository;

    public CreateSessionHandler(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void handle(StartContext context) {
        TarotSession session = TarotSession.builder()
                .sessionId(UUID.randomUUID().toString().replace("-", ""))
                .userId(context.getUserId())
                .question(context.getQuestion())
                .spread(context.getSpread())
                .drawnCards(new ArrayList<>())
                .build();
        sessionRepository.save(session);
        context.setSession(session);
    }

    @Override
    public int getOrder() {
        return 40;
    }
}
