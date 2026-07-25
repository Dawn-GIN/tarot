package com.dawn.tarot.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 统一响应结构。
 */
@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private String code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>("0000", "success", data);
    }

    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}
