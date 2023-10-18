package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.keys.RolesUsersKeys;
import com.smartvoucher.webEcommercesmartvoucher.entity.RolesUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesUsersRepository extends JpaRepository<RolesUsersEntity, RolesUsersKeys> {
}
