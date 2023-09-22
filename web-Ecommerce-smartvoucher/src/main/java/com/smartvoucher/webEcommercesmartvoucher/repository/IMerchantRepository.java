package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.MerchantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMerchantRepository extends JpaRepository<MerchantEntity, Long> {
    MerchantEntity findOneByMerchantCode(String code);
    List<MerchantEntity> findAllByMerchantCode(String merchantCode);
}
