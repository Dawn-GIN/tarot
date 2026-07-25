package com.dawn.tarot.application.chain;

import com.dawn.tarot.domain.model.Spread;
import com.dawn.tarot.domain.model.TarotSession;

import lombok.Data;

/**
 * 开始占卜责任链上下文。
 */
@Data
public class StartContext {
    private Long userId;
    private String question;

    private Spread spread;
    private TarotSession session;
}
