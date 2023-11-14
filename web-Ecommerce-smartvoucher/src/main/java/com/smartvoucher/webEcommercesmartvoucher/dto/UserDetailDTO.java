package com.smartvoucher.webEcommercesmartvoucher.dto;

import jdk.jshell.Snippet;
import lombok.*;

import java.sql.Timestamp;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDTO {

    private String avatarUrl;

    private String firstName;

    private String lastName;

    private String userName;

    private String fullName;

    private String phone;

    private String email;

    private String address;

}
