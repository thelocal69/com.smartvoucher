
package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.WarehouseStoreEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.keys.WarehouseStoreKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseStoreRepository extends JpaRepository<WarehouseStoreEntity, WarehouseStoreKeys> {
    @Query("SELECT ws FROM warehouse_stores ws " +
            "JOIN warehouse w ON ws.keys.idWarehouse = w.id " +
            "JOIN store s ON ws.keys.idStore = s.id")
    List<WarehouseStoreEntity> getAllWarehouseStore();
    WarehouseStoreEntity findByKeys(WarehouseStoreKeys keys);
    @Query("SELECT ws FROM warehouse_stores ws " +
            "JOIN warehouse w ON ws.keys.idWarehouse = w.id " +
            "JOIN store s ON ws.keys.idStore = s.id " +
            "WHERE w.warehouseCode = ?1 AND s.storeCode = ?2 ")
    WarehouseStoreEntity getByWarehouseCodeAndStoreCode (String warehouseCode, String storeCode);
    void deleteByKeys(WarehouseStoreKeys keys);

    @Query("SELECT ws FROM warehouse_stores ws " +
            "JOIN warehouse w ON ws.keys.idWarehouse = w.id " +
            "JOIN store s ON ws.keys.idStore = s.id " +
            "WHERE w.id = ?1")
    WarehouseStoreEntity getWarehouseStoreEntitiesByIdWarehouse(long id);

    @Query("SELECT ws.keys.idStore FROM warehouse_stores ws " +
            "JOIN warehouse w ON ws.keys.idWarehouse = w.id " +
            "JOIN store s ON ws.keys.idStore = s.id " +
            "WHERE w.id = :id")
    Long getIdStoreByIdWarehouse(long id);

}
