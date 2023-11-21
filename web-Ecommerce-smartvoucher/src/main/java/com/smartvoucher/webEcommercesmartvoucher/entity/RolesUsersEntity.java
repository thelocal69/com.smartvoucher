package com.smartvoucher.webEcommercesmartvoucher.entity;

import com.smartvoucher.webEcommercesmartvoucher.entity.keys.RolesUsersKeys;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_by")
    private String updateBy;
    @Column(name = "updated_at")
    private Timestamp updateAt;
}
