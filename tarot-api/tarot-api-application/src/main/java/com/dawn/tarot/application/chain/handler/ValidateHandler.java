package com.dawn.tarot.application.chain.handler;

import org.springframework.stereotype.Component;

import com.dawn.tarot.application.chain.StartContext;
import com.dawn.tarot.application.chain.StartHandler;
import com.dawn.tarot.domain.exception.TarotException;

/**
 * 参数校验节点。
 */
@Component
public class ValidateHandler implements StartHandler {

    @Override
    public void handle(StartContext context) {
        String question = context.getQuestion();
        if (question == null || question.isBlank()) {
            throw new TarotException("INVALID_QUESTION", "问题不能为空");
        }
        if (question.length() > 200) {
            throw new TarotException("INVALID_QUESTION", "问题过长,请精简到 200 字以内");
        }
        if (context.getUserId() == null) {
            throw new TarotException("INVALID_USER", "缺少用户身份");
        }
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
