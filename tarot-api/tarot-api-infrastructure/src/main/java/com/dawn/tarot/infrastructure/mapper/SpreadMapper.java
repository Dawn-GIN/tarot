package com.dawn.tarot.infrastructure.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dawn.tarot.infrastructure.po.SpreadPO;

@Mapper
public interface SpreadMapper {

    List<SpreadPO> selectAll();

    SpreadPO selectByCode(@Param("code") String code);
}
