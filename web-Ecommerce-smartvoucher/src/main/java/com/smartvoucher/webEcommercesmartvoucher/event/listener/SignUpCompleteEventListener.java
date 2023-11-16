package com.smartvoucher.webEcommercesmartvoucher.event.listener;

import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.enums.Provider;
import com.smartvoucher.webEcommercesmartvoucher.event.SignUpCompleteEvent;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IAccountService;
import com.smartvoucher.webEcommercesmartvoucher.util.EmailUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
public class SignUpCompleteEventListener implements ApplicationListener<SignUpCompleteEvent> {
    private final IAccountService accountService;
    private final UserRepository userRepository;
    private final EmailUtil emailUtil;

    @Autowired
    public SignUpCompleteEventListener(final IAccountService accountService,
                                       final UserRepository userRepository,
                                       final EmailUtil emailUtil) {
        this.accountService = accountService;
        this.userRepository = userRepository;
        this.emailUtil = emailUtil;
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
            emailUtil.sendVerificationEmail(url, user);
        }catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
