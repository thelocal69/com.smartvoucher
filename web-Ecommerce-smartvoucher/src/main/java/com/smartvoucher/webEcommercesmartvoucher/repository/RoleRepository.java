package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query("SELECT r FROM roles r WHERE r.name = ?1")
    Optional<RoleEntity> findByName (String roleName);

    @Query("SELECT r FROM roles r WHERE r.name = ?1 AND r.id != ?2")
    List<RoleEntity> findByNameAndId (String roleName , long id);

}
