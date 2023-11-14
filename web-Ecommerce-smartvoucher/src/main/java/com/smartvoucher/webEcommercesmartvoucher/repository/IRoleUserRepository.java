package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.RolesUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleUserRepository extends JpaRepository<RolesUsersEntity, Long> {
    @Query("SELECT ru FROM roles_users ru" +
            " JOIN users u ON ru.roles_usersKeys.idUser = u.id" +
            " JOIN roles r ON  ru.roles_usersKeys.idRole = r.id" +
            " WHERE u.email = :email")
    RolesUsersEntity getEmail(String email);
}
