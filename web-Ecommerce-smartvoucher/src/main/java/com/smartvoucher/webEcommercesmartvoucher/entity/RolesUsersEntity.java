package com.smartvoucher.webEcommercesmartvoucher.entity;

import com.smartvoucher.webEcommercesmartvoucher.entity.keys.RolesUsersKeys;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "roles_users")
public class RolesUsersEntity {

    // Đặt PRIMARY KEY cho id_role và id_user
    @EmbeddedId
    private RolesUsersKeys roleUserKeys;
    // ----

    @ManyToOne
    @JoinColumn(name = "id_role",insertable = false, updatable = false)
    @NonNull                     // tránh update value tới những table được references tới
    private RoleEntity idRole;

    @ManyToOne
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    @NonNull
    private UserEntity idUser;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;
    @CreatedDate
    @Column(name = "created_at")
    private Timestamp createdAt;
    @LastModifiedBy
    @Column(name = "updated_by")
    private String updateBy;
    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updateAt;
}
