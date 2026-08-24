package com.dawn.tarot.interfaces.dto;

import com.dawn.tarot.application.service.VerificationCodePurpose;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SendCodeRequest {
    @Email(message = "请输入正确的邮箱地址") @NotNull(message = "邮箱不能为空") private String email;
    @NotNull(message = "验证码用途不能为空") private VerificationCodePurpose purpose;
}
