package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.MerchantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMerchantRepository extends JpaRepository<MerchantEntity, Long> {
    MerchantEntity findOneByMerchantCode(String code);
    MerchantEntity findOnByName(String name);
    List<MerchantEntity> findAllByMerchantCode(String merchantCode);
    @Query("SELECT m.name FROM merchant m")
    List<String> getAllMerchantName();
    MerchantEntity findOneById(Long id);
    Boolean existsByName(String merchantName);
    List<MerchantEntity> findAllByStatus(int status);
    List<MerchantEntity> searchMerchantEntityByNameContainingIgnoreCase(String name);
}
