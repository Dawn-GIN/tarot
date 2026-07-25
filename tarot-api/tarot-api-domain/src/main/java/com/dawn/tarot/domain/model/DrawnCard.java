package com.dawn.tarot.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 一次占卜中被抽到的牌:卡牌 + 正逆位 + 所在位置。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawnCard {
    private Integer id;
    private String name;
    private String image;
    private boolean reversed;
    /** 对应牌型中的位置索引 */
    private Integer positionIndex;
}
