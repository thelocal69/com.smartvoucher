package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.keys.Roles_UsersKeys;
import com.smartvoucher.webEcommercesmartvoucher.entity.Roles_UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Roles_UsersRepository extends JpaRepository<Roles_UsersEntity, Roles_UsersKeys> {
}
