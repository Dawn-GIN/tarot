package com.dawn.tarot.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 塔罗卡牌元数据。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private Integer id;
    private String name;
    private String nameEn;
    /** major / minor */
    private String arcana;
    private String suit;
    private String rank;
    private String image;
}
