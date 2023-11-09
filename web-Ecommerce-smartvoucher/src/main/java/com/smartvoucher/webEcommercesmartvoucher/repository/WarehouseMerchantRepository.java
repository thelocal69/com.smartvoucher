
package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.WarehouseMerchantEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.keys.WarehouseMerchantKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseMerchantRepository extends JpaRepository<WarehouseMerchantEntity, WarehouseMerchantKeys> {

    @Query("SELECT wm FROM warehouse_merchant wm " +
            "JOIN merchant m ON wm.keys.idMerchant = m.id " +
            "JOIN warehouse w ON wm.keys.idWarehouse = w.id " +
            "JOIN roles r ON wm.idRole = r.id" )
    List<WarehouseMerchantEntity> getAllWarehouseMerchant();
    WarehouseMerchantEntity findByKeys (WarehouseMerchantKeys keys);
    @Query("SELECT wm FROM warehouse_merchant wm " +
            "JOIN merchant m ON wm.keys.idMerchant = m.id " +
            "JOIN warehouse w ON wm.keys.idWarehouse = w.id " +
            "JOIN roles r ON wm.idRole = r.id " +
            "WHERE w.warehouseCode = ?1 AND m.merchantCode = ?2 " )
    WarehouseMerchantEntity getByWarehouseCodeAndMerchantCode(String warehouseCode, String merchantCode);
    void deleteByKeys(WarehouseMerchantKeys keys);

}
