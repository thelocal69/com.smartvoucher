package com.smartvoucher.webEcommercesmartvoucher.util;

import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
public class EmailUtil {
    private final JavaMailSender javaMailSender;
    private final MailProperties mailProperties;
    private final StringsUtil stringsUtil;

    @Autowired
    public EmailUtil(final JavaMailSender javaMailSender
            , final MailProperties mailProperties,
                     final StringsUtil stringsUtil) {
        this.javaMailSender = javaMailSender;
        this.mailProperties = mailProperties;
        this.stringsUtil = stringsUtil;
    }

    public void sendVerificationEmail(String url, UserEntity user) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email xác nhận";
        String senderName = "Cổng dịch vụ đăng ký tài khoản người dùng Smartvoucher.com";
        String mailContent = "<p> Xin chào, "+ stringsUtil.getUserNameFormDomain(user.getEmail())+ ", </p>"+
                "<p>Cảm ơn bạn đã đăng ký. "+
                "Xin hãy, Bấm vào đường link bên dưới để hoàn thành xác nhận.</p>"+
                "<a href=\"" +url+ "\">Xác nhận email để kích hoạt tài khoản</a>"+
                "<p> Xin cảm ơn <br> Cổng dịch vụ đăng ký tài khoản người dùng Smartvoucher.com";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(mailProperties.getUsername(), senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(message);
    }

    public void sendResetPassword(String email) throws MessagingException, UnsupportedEncodingException{
        String url = "http://localhost:8082/account/api/set_password";
        String subject = "Đặt lại mật khẩu";
        String senderName = "Cổng dịch vụ đăng ký tài khoản người dùng Smartvoucher.com";
        String mailContent = "<p> Xin chào, "+ stringsUtil.getUserNameFormDomain(email)+", </p>"+
                "<a href=\"" +url+ "\">Đặt lại mật khẩu</a>"+
                "<p> Xin cảm ơn <br> Cổng dịch vụ đăng ký tài khoản người dùng Smartvoucher.com";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(mailProperties.getUsername(), senderName);
        messageHelper.setTo(email);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(message);
    }

    public void sendTicketCode(String mail, List<TicketDTO> listVoucherCode) throws MessagingException, UnsupportedEncodingException {
            String senderName = "Cổng dịch vụ thanh toán mua voucher của người dùng Smartvoucher.com";
            String subject = "Thanh toán Voucher thành công";
            StringBuilder textBuilder = new StringBuilder();
            textBuilder.append(" <p> Xin chào, người dùng ").append(stringsUtil.getUserNameFormDomain(mail)).append(" <br> Đây là mã voucher : <br>");
            for (int i = 0; i < listVoucherCode.size(); i++) {
                textBuilder.append("<span style='font-size: 17px; font-weight: 700;'>")
                        .append(i).append(". ")
                        .append(listVoucherCode.get(i).getIdSerialDTO().getSerialCode())
                        .append("</span> <br>");
            }
            textBuilder.append(" </span> <br> Xin cảm ơn bạn đã sử dụng dịch vụ bên <a href='#'>Smartvoucher.com</a> của chúng tôi. </p>");
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(mailProperties.getUsername(), senderName);
            messageHelper.setTo(mail);
            messageHelper.setSubject(subject);
            messageHelper.setText(String.valueOf(textBuilder), true);
            javaMailSender.send(message);
            System.out.println("Send mail success!");
    }
}
