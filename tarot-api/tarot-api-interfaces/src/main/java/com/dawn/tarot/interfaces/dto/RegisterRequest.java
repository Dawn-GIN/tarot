package com.dawn.tarot.interfaces.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @Email(message = "请输入正确的邮箱地址") @NotBlank(message = "邮箱不能为空") private String email;
    @Size(min = 8, max = 72, message = "密码长度需为 8 至 72 位") private String password;
    @NotBlank(message = "验证码不能为空") private String code;
}
