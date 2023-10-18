package com.smartvoucher.webEcommercesmartvoucher.entity.keys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RolesUsersKeys implements Serializable {

    @Column(name = "id_role")
    private long idRole;

    @Column(name = "id_user")
    private long idUser;

}
