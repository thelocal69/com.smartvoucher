package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolesUsersDTO {

    private RolesDTO idRole;

    private UsersDTO idUser;

    private String createdBy;

    private String updatedBy;

    private Timestamp createdAt;

    private Timestamp updatedAt;

}
