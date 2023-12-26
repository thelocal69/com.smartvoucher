package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStoreRepository extends JpaRepository<StoreEntity, Long> {
    List<StoreEntity> findAllByStoreCode(String storeCode);
    List<StoreEntity> searchAllByNameContainingIgnoreCase(String name);
    StoreEntity findOneById(Long id);
    StoreEntity findOneByStoreCode(String storeCode);
    List<StoreEntity> findAllByStatus(int status);
}
