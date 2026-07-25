package com.dawn.tarot.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dawn.tarot.application.chain.StartContext;
import com.dawn.tarot.application.chain.StartHandler;
import com.dawn.tarot.domain.model.TarotSession;

/**
 * 开始占卜:按顺序执行责任链。
 */
@Service
public class TarotStartService {

    private final List<StartHandler> handlers;

    public TarotStartService(List<StartHandler> handlers) {
        // Spring 注入时按 Ordered 升序,这里再显式排序确保稳定
        this.handlers = handlers.stream()
                .sorted((a, b) -> Integer.compare(a.getOrder(), b.getOrder()))
                .toList();
    }

    public TarotSession start(Long userId, String question) {
        StartContext context = new StartContext();
        context.setUserId(userId);
        context.setQuestion(question);
        for (StartHandler handler : handlers) {
            handler.handle(context);
        }
        return context.getSession();
    }
}
