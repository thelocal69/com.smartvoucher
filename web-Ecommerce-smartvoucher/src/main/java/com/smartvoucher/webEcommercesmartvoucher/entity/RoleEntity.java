package com.smartvoucher.webEcommercesmartvoucher.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NotNull(message = "Please fill all information!")
    @NotBlank(message = "Please fill all information!")
    private String name;

    @Column(name = "role_code")
    @NotNull(message = "Please fill all information!")
    @NotBlank(message = "Please fill all information!")
    private String roleCode;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "created_at")
    @CreatedDate
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Timestamp updatedAt;

    // field được references
    @OneToMany(mappedBy = "idRole")
    private List<RolesUsersEntity> rolesUsersEntities;

    @OneToMany(mappedBy = "idRole")
    private List<WarehouseMerchantEntity> warehouseMerchantEntities;
}
