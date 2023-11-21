package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findOneById(Long id);
    List<UserEntity> findByEmailAndProviderOrPhone(String email, String provider,String phone);
    UserEntity findOneByEmail(String email);
    UserEntity findOneByMemberCode(String memberCode);
    List<UserEntity> findAllByMemberCode(String memberCode);
    UserEntity findOneByIdAndProvider(long id, String provider);
   UserEntity findByEmailAndProvider(String email, String provider);
}
