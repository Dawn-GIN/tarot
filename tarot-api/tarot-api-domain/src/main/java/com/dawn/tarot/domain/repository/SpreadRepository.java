package com.dawn.tarot.domain.repository;

import java.util.List;
import java.util.Optional;

import com.dawn.tarot.domain.model.Spread;

public interface SpreadRepository {

    List<Spread> findAll();

    Optional<Spread> findByCode(String code);
}
