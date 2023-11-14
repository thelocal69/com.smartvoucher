package com.smartvoucher.webEcommercesmartvoucher.service.oauth2;

import java.util.Map;

public class OAuth2GoogleUser extends OAuth2UserDetail{

    public OAuth2GoogleUser(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getAvatarURL() {
        return (String) attributes.get("picture");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getFirstName() {
        return (String) attributes.get("given_name");
    }

    @Override
    public String getLastName() {
        return (String) attributes.get("family_name");
    }
}
