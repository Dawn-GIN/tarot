package com.dawn.tarot.application.service;

/**
 * 占卜次数服务。本阶段为桩实现,后期对接 big-market 营销平台。
 */
public interface CreditService {

    /** 查询剩余次数 */
    int balance(Long userId);

    /** 扣减一次;次数不足抛 TarotException */
    void deduct(Long userId);
}
