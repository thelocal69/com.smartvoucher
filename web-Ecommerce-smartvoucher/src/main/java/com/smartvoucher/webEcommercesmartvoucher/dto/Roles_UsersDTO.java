package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Roles_UsersDTO {

    private RolesDTO idRole;

    private UsersDTO idUser;

    private String createdBy;

    private String updatedBy;

    private Timestamp createdAt;

    private Timestamp updatedAt;

}
