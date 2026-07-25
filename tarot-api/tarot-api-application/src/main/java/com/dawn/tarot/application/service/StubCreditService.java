package com.dawn.tarot.application.service;

import org.springframework.stereotype.Service;

/**
 * 次数服务桩:恒返回充足次数,扣减为空操作。
 * 后期替换为对接 big-market 的实现。
 */
@Service
public class StubCreditService implements CreditService {

    @Override
    public int balance(Long userId) {
        return 99;
    }

    @Override
    public void deduct(Long userId) {
        // no-op:本阶段不做真实扣减
    }
}
