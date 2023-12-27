package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.*;

import java.sql.Timestamp;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String memberCode;
    private String avatarUrl;

    private String firstName;

    private String lastName;

    private String userName;

    private String fullName;

    private String pwd;

    private String phone;

    private String email;

    private boolean enable;

    private String provider;

    private int status;

    private String address;

    private String createdBy;

    private String updatedBy;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
