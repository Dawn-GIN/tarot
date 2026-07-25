package com.dawn.tarot.interfaces.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawn.tarot.application.service.CreditService;
import com.dawn.tarot.interfaces.dto.ApiResponse;
import com.dawn.tarot.interfaces.support.UserContext;

@RestController
@RequestMapping("/api/credit")
public class CreditController {

    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @GetMapping("/balance")
    public ApiResponse<Map<String, Integer>> balance() {
        int remaining = creditService.balance(UserContext.currentUserId());
        return ApiResponse.ok(Map.of("remaining", remaining));
    }
}
