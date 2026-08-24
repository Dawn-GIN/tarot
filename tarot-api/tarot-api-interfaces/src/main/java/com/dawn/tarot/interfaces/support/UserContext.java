package com.dawn.tarot.interfaces.support;

import com.dawn.tarot.domain.exception.TarotException;

/** 当前请求认证用户；由 JWT 过滤器设置，响应结束后自动清理。 */
public final class UserContext {
    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    private UserContext() { }
    public static Long currentUserId() {
        Long userId = USER_ID.get();
        if (userId == null) throw TarotException.of("UNAUTHORIZED", "请先登录");
        return userId;
    }
    public static void set(Long userId) { USER_ID.set(userId); }
    public static void clear() { USER_ID.remove(); }
}
