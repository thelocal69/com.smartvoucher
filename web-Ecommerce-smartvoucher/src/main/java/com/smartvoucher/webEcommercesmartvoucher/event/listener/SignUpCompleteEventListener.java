package com.smartvoucher.webEcommercesmartvoucher.event.listener;

import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.enums.Provider;
import com.smartvoucher.webEcommercesmartvoucher.event.SignUpCompleteEvent;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IAccountService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
public class SignUpCompleteEventListener implements ApplicationListener<SignUpCompleteEvent> {
    private final IAccountService accountService;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    @Autowired
    public SignUpCompleteEventListener(final IAccountService accountService,
                                       final UserRepository userRepository,
                                       final JavaMailSender javaMailSender) {
        this.accountService = accountService;
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void onApplicationEvent(@NonNull SignUpCompleteEvent event) {
            //nhan 1 tai khoan dang ky
        UserEntity user = userRepository.findByEmailAndProvider(event.getSignUpDTO().getEmail(), Provider.local.name());
        //tao verification token cho user dang ky
        String verificationToken = UUID.randomUUID().toString().replace("-", "");
        //save verificaton token xuong db
        this.accountService.saveUserVerificationToken(user, verificationToken);
        //tao ra url verification de gui cho user
        String url = event.getApplicationURL()+"/account/api/verify_email?token="+verificationToken;
        //gui email xac nhan
        try {
            sendVerificationEmail(url, user);
        }catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendVerificationEmail(String url, UserEntity user) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email xác nhận";
        String senderName = "Cổng dịch vụ đăng ký tài khoản người dùng Smartvoucher.com";
        String mailContent = "<p> Xin chào, "+ user.getEmail().substring(0,10)+ ", </p>"+
                "<p>Cảm ơn bạn đã đăng ký. "+
                "Xin hãy, Bấm vào đường link bên dưới để hoàn thành xác nhận.</p>"+
                "<a href=\"" +url+ "\">Xác nhận email để kích hoạt tài khoản</a>"+
                "<p> Xin cảm ơn <br> Cổng dịch vụ đăng ký tài khoản người dùng Smartvoucher.com";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("cybersoftprojectvoucher@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(message);
    }
}
