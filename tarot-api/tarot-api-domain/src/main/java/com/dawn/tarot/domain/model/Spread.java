package com.dawn.tarot.domain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 塔罗牌型(如单张、三张、凯尔特十字)。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Spread {
    private String code;
    private String name;
    private String description;
    private Integer cardCount;
    private List<SpreadPosition> positions;
}
