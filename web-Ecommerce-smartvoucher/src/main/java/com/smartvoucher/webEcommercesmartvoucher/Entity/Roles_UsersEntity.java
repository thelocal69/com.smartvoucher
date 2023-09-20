package com.smartvoucher.webEcommercesmartvoucher.Entity;

import com.smartvoucher.webEcommercesmartvoucher.Entity.Keys.Roles_UsersKeys;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@Entity(name = "roles_users")
public class Roles_UsersEntity {

    // Đặt PRIMARY KEY cho id_role và id_user
    @EmbeddedId
    private Roles_UsersKeys roles_usersKeys;
    // ----

    @ManyToOne
    @JoinColumn(name = "id_role",insertable = false, updatable = false)
    @NonNull                     // tránh update value tới những table được references tới
    private RolesEntity idRole;

    @ManyToOne
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    @NonNull
    private UsersEntity idUser;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
