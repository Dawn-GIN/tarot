package com.dawn.tarot.infrastructure.mail;

import java.nio.charset.StandardCharsets;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.dawn.tarot.domain.exception.TarotException;
import com.dawn.tarot.domain.service.VerificationMailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

/** 通过 SMTP 发送账号验证码；QQ 邮箱等标准 SMTP 服务均可使用。 */
@Slf4j
@Service
public class SmtpVerificationMailService implements VerificationMailService {

    private final JavaMailSender mailSender;
    private final MailProperties properties;

    public SmtpVerificationMailService(JavaMailSender mailSender, MailProperties properties) {
        this.mailSender = mailSender;
        this.properties = properties;
    }

    @Override
    public void sendRegistrationCode(String recipient, String code) {
        sendCode(recipient, code, "注册账号");
    }

    @Override
    public void sendPasswordResetCode(String recipient, String code) {
        sendCode(recipient, code, "重置密码");
    }

    private void sendCode(String recipient, String code, String scene) {
        if (properties.getFrom() == null || properties.getFrom().isBlank()) {
            throw TarotException.of("MAIL_NOT_CONFIGURED", "邮件服务尚未配置");
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, StandardCharsets.UTF_8.name());
            helper.setFrom(properties.getFrom());
            helper.setTo(recipient);
            helper.setSubject("【星语塔罗】" + scene + "验证码");
            helper.setText(textContent(code, scene), htmlContent(code, scene));
            mailSender.send(message);
            log.info("账号验证码邮件已发送: scene={}, recipient={}", scene, recipient);
        } catch (MessagingException | MailException e) {
            log.error("账号验证码邮件发送失败: scene={}, recipient={}", scene, recipient, e);
            throw TarotException.of("MAIL_SEND_FAILED", "验证码邮件发送失败，请稍后重试");
        }
    }

    private String textContent(String code, String scene) {
        return "你正在" + scene + "，验证码为：" + code + "。验证码 10 分钟内有效，请勿向他人透露。";
    }

    private String htmlContent(String code, String scene) {
        return "<div style=\"font-family:Arial,'Microsoft YaHei',sans-serif;color:#24212b;line-height:1.6\">"
                + "<h2 style=\"color:#7357b8\">星语塔罗</h2>"
                + "<p>你正在" + scene + "，验证码为：</p>"
                + "<p style=\"font-size:28px;font-weight:700;letter-spacing:6px;color:#4c337d\">" + code + "</p>"
                + "<p>验证码 10 分钟内有效，请勿向他人透露。</p></div>";
    }
}
