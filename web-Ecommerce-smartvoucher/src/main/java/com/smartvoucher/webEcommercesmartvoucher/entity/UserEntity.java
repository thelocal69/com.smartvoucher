package com.smartvoucher.webEcommercesmartvoucher.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Entity(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "member_code")
    private String memberCode;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String pwd;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private int status;

    @Column(name = "address")
    private String address;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    // field được references
    @OneToMany(mappedBy = "idUser")
    private List<TicketEntity> ticketEntity;

    @OneToMany(mappedBy = "idUser")
    private List<OrderEntity> orderEntity;

    @OneToMany(mappedBy = "idUser")
    private List<RolesUsersEntity> rolesUsersEntities;
}
