package com.smartvoucher.webEcommercesmartvoucher.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Component
public class EmailUtil {
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailUtil(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendResetPassword(String email) throws MessagingException, UnsupportedEncodingException{
        String url = "http://localhost:8082/account/api/set_password";
        String subject = "Đặt lại mật khẩu";
        String senderName = "Cổng dịch vụ đăng ký tài khoản người dùng Smartvoucher.com";
        String mailContent = "<p> Xin chào, "+ email.substring(0, 10)+", </p>"+
                "<a href=\"" +url+ "\">Đặt lại mật khẩu</a>"+
                "<p> Xin cảm ơn <br> Cổng dịch vụ đăng ký tài khoản người dùng Smartvoucher.com";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("cybersoftprojectvoucher@gmail.com", senderName);
        messageHelper.setTo(email);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(message);
    }
}
