package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IWareHouseRepository extends JpaRepository<WareHouseEntity, Long> {
    List<WareHouseEntity> findAllByWarehouseCode(String wareHouseCode);
    WareHouseEntity findOneById(Long id);
}
