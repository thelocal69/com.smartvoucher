package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String userName);
    List<UserEntity> findByEmailOrPhone(String email,String phone);
    UserEntity findOneByEmail(String email);
    UserEntity findOneByMemberCode(String memberCode);
    List<UserEntity> findAllByMemberCode(String memberCode);

}
