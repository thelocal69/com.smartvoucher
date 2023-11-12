
package com.smartvoucher.webEcommercesmartvoucher.dto;

import com.smartvoucher.webEcommercesmartvoucher.entity.keys.WarehouseStoreKeys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseStoreDTO {
    private WarehouseStoreKeys keys;
    private long idWarehouse;
    private long idStore;
    private String createdBy;
    private String updatedBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String warehouseCode;
    private String storeCode;


}
