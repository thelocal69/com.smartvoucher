package com.smartvoucher.webEcommercesmartvoucher.service.oauth2;

import java.util.Map;

public abstract class OAuth2UserDetail {
    protected Map<String, Object> attributes;

    public OAuth2UserDetail(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getAvatarURL();
    public abstract String getName();
    public abstract String getEmail();
    public abstract String getFirstName();
    public abstract String getLastName();
}
