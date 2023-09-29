package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IStoreRepository extends JpaRepository<StoreEntity, Long> {
    List<StoreEntity> findAllByStoreCode(String storeCode);
    StoreEntity findOneById(Long id);
}
