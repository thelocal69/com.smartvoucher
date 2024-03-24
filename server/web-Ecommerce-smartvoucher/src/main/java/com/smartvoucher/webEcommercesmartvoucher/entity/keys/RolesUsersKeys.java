package com.smartvoucher.webEcommercesmartvoucher.entity.keys;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Builder
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RolesUsersKeys)) return false;
        RolesUsersKeys that = (RolesUsersKeys) o;
        return idRole == that.idRole && idUser == that.idUser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRole, idUser);
    }
}
