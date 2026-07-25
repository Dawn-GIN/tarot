package com.dawn.tarot.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 牌型中的一个位置定义。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpreadPosition {
    private Integer index;
    private String label;
    private String description;
}
