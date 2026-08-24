package com.dawn.tarot.domain.service;

/**
 * 账号相关的验证码邮件发送能力。
 */
public interface VerificationMailService {

    void sendRegistrationCode(String recipient, String code);

    void sendPasswordResetCode(String recipient, String code);
}
