package com.dawn.tarot.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DrawRequest {

    @NotBlank(message = "sessionId 不能为空")
    private String sessionId;
}
