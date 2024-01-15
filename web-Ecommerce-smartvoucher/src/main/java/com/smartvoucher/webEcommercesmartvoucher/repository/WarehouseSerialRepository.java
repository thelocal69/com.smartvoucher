
package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WarehouseSerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.keys.WarehouseSerialKeys;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseSerialRepository extends JpaRepository <WarehouseSerialEntity, WarehouseSerialKeys> {

    @Query("SELECT COUNT(*) as total FROM warehouse_serial ws WHERE ws.idWarehouse = ?1")
    int total (WareHouseEntity wareHouseEntity);
    @Query("SELECT COUNT(*) FROM warehouse_serial ws JOIN warehouse w ON ws.idWarehouse.id=w.id" +
            " WHERE w.id=:id")
    int countIdWarehouse (Long id);
    @Query("SELECT ws FROM warehouse_serial ws " +
            "JOIN warehouse w ON ws.keys.idWarehouse = w.id " +
            "JOIN serial s ON ws.keys.idSerial = s.id")
    List<WarehouseSerialEntity> getAllWarehouseSerial();
    List<WarehouseSerialEntity> findAllByIdWarehouse(WareHouseEntity wareHouseEntity, Pageable pageable);
    @Query("SELECT ws FROM warehouse_serial ws " +
            "JOIN warehouse w ON ws.keys.idWarehouse = w.id " +
            "JOIN serial s ON ws.keys.idSerial = s.id " +
            "WHERE w.warehouseCode = ?1 AND s.serialCode = ?2 " )
    WarehouseSerialEntity getByWarehouseCodeAndSerialCode(String warehouseCode, String serialCode);
    void deleteByKeys(WarehouseSerialKeys keys);

    void deleteByIdSerial(SerialEntity idSerial);

}
