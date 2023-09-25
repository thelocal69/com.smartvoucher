package com.smartvoucher.webEcommercesmartvoucher.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {

    private long id;

    private String memberCode;

    private String firstName;

    private String lastName;

    private String userName;

    private String fullName;

    private String pwd;

    private String phone;

    private String email;

    private int status;

    private String address;

    private String createdBy;

    private String updatedBy;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
