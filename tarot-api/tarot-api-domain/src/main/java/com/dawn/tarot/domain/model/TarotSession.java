package com.dawn.tarot.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 占卜会话:关联一次完整占卜(问题 → 牌型 → 抽卡 → 解读),存于 Redis。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarotSession implements Serializable {

    private String sessionId;
    private Long userId;
    private String question;
    private Spread spread;
    @Builder.Default
    private List<DrawnCard> drawnCards = new ArrayList<>();
    private String interpretation;

    /** 是否已抽满牌型所需数量 */
    @JsonIgnore
    public boolean isFullyDrawn() {
        return spread != null && drawnCards != null
                && drawnCards.size() >= spread.getCardCount();
    }

    /** 下一张待抽卡应放入的位置索引;已抽满返回 -1 */
    public int nextPositionIndex() {
        if (drawnCards == null) {
            return 0;
        }
        return isFullyDrawn() ? -1 : drawnCards.size();
    }
}
