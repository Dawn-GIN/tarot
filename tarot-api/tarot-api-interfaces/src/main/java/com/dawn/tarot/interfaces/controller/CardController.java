package com.dawn.tarot.interfaces.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawn.tarot.application.service.CardQueryService;
import com.dawn.tarot.domain.model.Card;
import com.dawn.tarot.interfaces.dto.ApiResponse;

@RestController
@RequestMapping("/api/tarot/cards")
public class CardController {

    private final CardQueryService cardQueryService;

    public CardController(CardQueryService cardQueryService) {
        this.cardQueryService = cardQueryService;
    }

    @GetMapping("/{id}")
    public ApiResponse<Card> getCard(@PathVariable Integer id) {
        return ApiResponse.ok(cardQueryService.getById(id));
    }
}
