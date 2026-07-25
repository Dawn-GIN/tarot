package com.dawn.tarot.interfaces.support;

/**
 * 用户身份桩:鉴权未接入前固定返回演示用户。
 * 后期由登录态(JWT/Session)解析真实 userId。
 */
public final class UserContext {

    private static final Long DEMO_USER_ID = 1L;

    private UserContext() {
    }

    public static Long currentUserId() {
        return DEMO_USER_ID;
    }
}
