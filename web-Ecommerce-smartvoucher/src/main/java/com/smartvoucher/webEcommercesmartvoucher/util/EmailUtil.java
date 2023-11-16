package com.smartvoucher.webEcommercesmartvoucher.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Component
public class EmailUtil {
    private final JavaMailSender javaMailSender;

    private final MailProperties mailProperties;

    @Autowired
    public EmailUtil(final JavaMailSender javaMailSender
            , final MailProperties mailProperties) {
        this.javaMailSender = javaMailSender;
        this.mailProperties = mailProperties;
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

    public void sendTicketCode(String mail, String voucherCode) throws MessagingException, UnsupportedEncodingException {
            String senderName = "Cổng dịch vụ thanh toán mua voucher của người dùng Smartvoucher.com";
            String subject = "Thanh toán Voucher thành công";
            String text = " <p> Xin chào, người dùng " + mail +
                "       <br> Đây là mã voucher : <span style='font-size: 17px; font-weight: 700;'> " + voucherCode + " </span>" +
                "       <br> Xin cảm ơn bạn đã sử dụng dịch vụ bên <a href='#'>Smartvoucher.com</a> của chúng tôi. </p>";
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(mailProperties.getUsername(), senderName);
            messageHelper.setTo(mail);
            messageHelper.setSubject(subject);
            messageHelper.setText(text, true);
            javaMailSender.send(message);
            System.out.println("Send mail success!");
    }
}
