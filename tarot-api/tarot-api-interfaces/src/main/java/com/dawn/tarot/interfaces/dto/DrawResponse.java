package com.dawn.tarot.interfaces.dto;

import com.dawn.tarot.application.service.DrawResult;
import com.dawn.tarot.domain.model.DrawnCard;

import lombok.Data;

/**
 * 抽卡响应,card 结构对齐前端(id,name,image,reversed,positionIndex)。
 */
@Data
public class DrawResponse {
    private DrawnCard card;
    private int drawnCount;
    private int totalCount;
    private boolean complete;

    public static DrawResponse from(DrawResult result) {
        DrawResponse resp = new DrawResponse();
        resp.card = result.getCard();
        resp.drawnCount = result.getDrawnCount();
        resp.totalCount = result.getTotalCount();
        resp.complete = result.isComplete();
        return resp;
    }
}
