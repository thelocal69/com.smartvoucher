package com.smartvoucher.webEcommercesmartvoucher.Entity.Keys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Roles_UsersKeys implements Serializable {

    @Column(name = "id_role")
    private long idRole;

    @Column(name = "id_user")
    private long idUser;

}
