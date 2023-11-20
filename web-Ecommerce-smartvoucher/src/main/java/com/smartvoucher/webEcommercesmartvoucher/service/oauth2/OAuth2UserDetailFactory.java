package com.smartvoucher.webEcommercesmartvoucher.service.oauth2;

import com.smartvoucher.webEcommercesmartvoucher.entity.enums.Provider;
import com.smartvoucher.webEcommercesmartvoucher.exception.OAuth2LoginException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class OAuth2UserDetailFactory {

    public static OAuth2UserDetail getOAuth2UserDetail(String registrationId, Map<String, Object> attributes){
        if (registrationId.equals(Provider.google.name())){
            return new OAuth2GoogleUser(attributes);
        }else {
            log.info("Sorry ! sign-in with "+registrationId+" is not supported !");
            throw new OAuth2LoginException(400, "Sorry ! sign-in with "+registrationId+" is not supported !");
        }
    }
}
