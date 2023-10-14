package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, Long> {

    @Query("SELECT r FROM roles r WHERE r.name = ?1")
    Optional<RolesEntity> findByName (String roleName);

    @Query("SELECT r FROM roles r WHERE r.name = ?1 AND r.id != ?2")
    List<RolesEntity> findByNameAndId (String roleName , long id);

}
