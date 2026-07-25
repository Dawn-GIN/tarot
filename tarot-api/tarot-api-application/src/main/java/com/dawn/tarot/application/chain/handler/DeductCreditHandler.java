package com.dawn.tarot.application.chain.handler;

import org.springframework.stereotype.Component;

import com.dawn.tarot.application.chain.StartContext;
import com.dawn.tarot.application.chain.StartHandler;
import com.dawn.tarot.application.service.CreditService;
import com.dawn.tarot.domain.exception.TarotException;

/**
 * 次数校验与扣减节点。
 */
@Component
public class DeductCreditHandler implements StartHandler {

    private final CreditService creditService;

    public DeductCreditHandler(CreditService creditService) {
        this.creditService = creditService;
    }

    @Override
    public void handle(StartContext context) {
        if (creditService.balance(context.getUserId()) <= 0) {
            throw new TarotException("NO_CREDIT", "今日占卜次数已用完,请签到获取次数");
        }
        creditService.deduct(context.getUserId());
    }

    @Override
    public int getOrder() {
        return 20;
    }
}
