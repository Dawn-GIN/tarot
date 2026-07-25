package com.dawn.tarot.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.dawn.tarot.domain.exception.TarotException;
import com.dawn.tarot.domain.model.Spread;
import com.dawn.tarot.domain.model.SpreadPosition;
import com.dawn.tarot.domain.repository.SpreadRepository;
import com.dawn.tarot.infrastructure.mapper.SpreadMapper;
import com.dawn.tarot.infrastructure.po.SpreadPO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class SpreadRepositoryImpl implements SpreadRepository {

    private final SpreadMapper spreadMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SpreadRepositoryImpl(SpreadMapper spreadMapper) {
        this.spreadMapper = spreadMapper;
    }

    @Override
    public List<Spread> findAll() {
        return spreadMapper.selectAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Spread> findByCode(String code) {
        SpreadPO po = spreadMapper.selectByCode(code);
        return Optional.ofNullable(po).map(this::toDomain);
    }

    private Spread toDomain(SpreadPO po) {
        List<SpreadPosition> positions;
        try {
            positions = objectMapper.readValue(po.getPositions(),
                    new TypeReference<List<SpreadPosition>>() {});
        } catch (Exception e) {
            throw new TarotException("SPREAD_PARSE_ERROR", "牌型位置解析失败: " + e.getMessage());
        }
        return Spread.builder()
                .code(po.getCode())
                .name(po.getName())
                .description(po.getDescription())
                .cardCount(po.getCardCount())
                .positions(positions)
                .build();
    }
}
