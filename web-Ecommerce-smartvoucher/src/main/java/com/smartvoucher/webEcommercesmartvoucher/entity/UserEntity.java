package com.smartvoucher.webEcommercesmartvoucher.entity;


import com.smartvoucher.webEcommercesmartvoucher.config.JpaAuditingConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "member_code")
    private String memberCode;

    @Column(name = "ava_url")
    private String avatarUrl;

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

    @Column(name = "balance")
    private double balance;

    @Column(name = "enable")
    private boolean isEnable;

    @Column(name = "provider")
    private String provider;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @CreatedDate
    @Column(name = "created_at")
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    // field được references
    @OneToMany(mappedBy = "idUser")
    private List<TicketEntity> ticketEntity;

    @OneToMany(mappedBy = "idUser")
    private List<OrderEntity> orderEntity;

    @OneToMany(mappedBy = "idUser")
    private Set<RolesUsersEntity> rolesUsersEntities;

    @OneToMany(mappedBy = "idUser")
    private List<CommentEntity> commentEntities;
}
