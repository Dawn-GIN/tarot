package com.dawn.tarot.application.chain;

import org.springframework.core.Ordered;

/**
 * 开始占卜责任链节点。按 getOrder() 升序执行。
 */
public interface StartHandler extends Ordered {

    void handle(StartContext context);
}
