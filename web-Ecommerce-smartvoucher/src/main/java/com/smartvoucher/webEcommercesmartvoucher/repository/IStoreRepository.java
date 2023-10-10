package com.smartvoucher.webEcommercesmartvoucher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStoreRepository extends JpaRepository<StoreEntity, Long> {
    List<StoreEntity> findAllByStoreCode(String storeCode);
    StoreEntity findOneById(Long id);
}
