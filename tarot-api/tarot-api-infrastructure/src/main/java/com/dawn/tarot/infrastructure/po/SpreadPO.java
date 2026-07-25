package com.dawn.tarot.infrastructure.po;

import lombok.Data;

/**
 * spread 表持久化对象,positions 为原始 JSON 字符串。
 */
@Data
public class SpreadPO {
    private String code;
    private String name;
    private String description;
    private Integer cardCount;
    private String positions;
}
