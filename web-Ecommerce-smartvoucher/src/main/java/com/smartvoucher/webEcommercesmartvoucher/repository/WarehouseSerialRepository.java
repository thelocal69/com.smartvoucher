package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WarehouseSerialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseSerialRepository extends JpaRepository<WarehouseSerialEntity, Long> {

    @Query("SELECT COUNT(*) as total FROM warehouse_serial ws WHERE ws.idWarehouse = ?1")
    int total (WareHouseEntity wareHouseEntity);
}
