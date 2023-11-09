package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWareHouseRepository extends JpaRepository<WareHouseEntity, Long> {
    List<WareHouseEntity> findAllByWarehouseCode(String wareHouseCode);
    WareHouseEntity findOneById(Long id);
    WareHouseEntity findOneByWarehouseCode(String warehouseCode);
}
