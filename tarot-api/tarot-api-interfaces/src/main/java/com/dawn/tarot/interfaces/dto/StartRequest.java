package com.dawn.tarot.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StartRequest {

    @NotBlank(message = "问题不能为空")
    @Size(max = 200, message = "问题过长,请精简到 200 字以内")
    private String question;
}
