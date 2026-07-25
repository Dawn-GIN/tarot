package com.dawn.tarot.interfaces.dto;

import com.dawn.tarot.domain.model.Spread;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 开始占卜响应,spread 结构对齐前端 mock/spreads.js。
 */
@Data
@AllArgsConstructor
public class StartResponse {
    private String sessionId;
    private Spread spread;
}
