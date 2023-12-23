package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.MerchantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMerchantRepository extends JpaRepository<MerchantEntity, Long> {
    MerchantEntity findOneByMerchantCode(String code);
    List<MerchantEntity> findAllByMerchantCode(String merchantCode);
    MerchantEntity findOneById(Long id);
    Boolean existsByMerchantCode(String merchantCode);
    List<MerchantEntity> findAllByStatus(int status);
    List<MerchantEntity> searchMerchantEntityByNameContainingIgnoreCase(String name);
}
