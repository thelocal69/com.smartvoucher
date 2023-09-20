package com.smartvoucher.webEcommercesmartvoucher.Repository;

import com.smartvoucher.webEcommercesmartvoucher.Entity.Keys.Roles_UsersKeys;
import com.smartvoucher.webEcommercesmartvoucher.Entity.Roles_UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Roles_UsersRepository extends JpaRepository<Roles_UsersEntity, Roles_UsersKeys> {
}
