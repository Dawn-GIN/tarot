package com.dawn.tarot.application.service;

import com.dawn.tarot.domain.model.DrawnCard;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 单次抽卡结果。
 */
@Data
@AllArgsConstructor
public class DrawResult {
    private DrawnCard card;
    /** 已抽数量 */
    private int drawnCount;
    /** 牌型所需总数 */
    private int totalCount;
    /** 是否已抽满 */
    private boolean complete;
}
