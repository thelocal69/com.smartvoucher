package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.enums.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findOneById(Long id);
    List<UserEntity> findByEmailAndProviderOrPhone(String email, String provider,String phone);
    UserEntity findOneByEmail(String email);
    UserEntity findOneByMemberCode(String memberCode);
    List<UserEntity> findAllByMemberCode(String memberCode);
    UserEntity findOneByIdAndProvider(long id, String provider);
   List<UserEntity> searchAllByEmailContainingIgnoreCase(String email);
   UserEntity findByEmailAndProvider(String email, String provider);
    UserEntity findByEmailAndProviderAndStatus(String email, String provider, int status);
}
