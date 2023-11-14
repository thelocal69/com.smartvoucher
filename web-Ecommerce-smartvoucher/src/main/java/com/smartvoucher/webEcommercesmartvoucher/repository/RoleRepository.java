package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByNameOrRoleCode (String roleName, String roleCode);
    RoleEntity findByRoleCodeAndId(String roleCode, long id);

    @Query("FROM roles r WHERE r.name = ?1 AND r.id != ?2")
    List<RoleEntity> findByNameAndId(String name, long id);

    RoleEntity findOneByName(String roleName);
    RoleEntity findOneByRoleCode(String roleCode);
    List<RoleEntity> findAllByName(String roleName);
    RoleEntity findOneById(long id);
}
