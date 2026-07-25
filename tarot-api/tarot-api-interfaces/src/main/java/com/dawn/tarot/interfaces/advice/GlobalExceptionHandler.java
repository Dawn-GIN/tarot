package com.dawn.tarot.interfaces.advice;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dawn.tarot.domain.exception.TarotException;
import com.dawn.tarot.interfaces.dto.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TarotException.class)
    public ApiResponse<Void> handleTarot(TarotException e) {
        log.warn("业务异常 code={} msg={}", e.getCode(), e.getMessage());
        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleValidation(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldError() != null
                ? e.getBindingResult().getFieldError().getDefaultMessage()
                : "参数校验失败";
        return ApiResponse.error("INVALID_PARAM", msg);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleUnknown(Exception e) {
        log.error("系统异常", e);
        return ApiResponse.error("SYSTEM_ERROR", "系统繁忙,请稍后再试");
    }
}
