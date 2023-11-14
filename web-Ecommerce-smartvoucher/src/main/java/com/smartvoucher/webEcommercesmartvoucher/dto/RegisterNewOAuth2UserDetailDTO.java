package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterNewOAuth2UserDetailDTO {
    private String memberCode;
    private String username;
    private String email;
    private String avatarURL;
    private String lastName;
    private String firstName;
    private String fullName;
    private String provider;
    private boolean enable;
    private int status;
    private String roleName;
    }
