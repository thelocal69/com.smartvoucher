package com.smartvoucher.webEcommercesmartvoucher.event;

import com.smartvoucher.webEcommercesmartvoucher.dto.SignUpDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SignUpCompleteEvent extends ApplicationEvent {
    private SignUpDTO signUpDTO;
    private String applicationURL;

    public SignUpCompleteEvent(SignUpDTO signUpDTO, String applicationURL) {
        super(signUpDTO);
        this.signUpDTO = signUpDTO;
        this.applicationURL = applicationURL;
    }
}
