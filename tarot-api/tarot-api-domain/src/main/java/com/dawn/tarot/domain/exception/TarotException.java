package com.dawn.tarot.domain.exception;

import lombok.Getter;

/**
 * 业务异常,携带错误码。
 */
@Getter
public class TarotException extends RuntimeException {

    private final String code;

    public TarotException(String code, String message) {
        super(message);
        this.code = code;
    }

    public static TarotException of(String code, String message) {
        return new TarotException(code, message);
    }
}
