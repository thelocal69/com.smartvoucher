package com.smartvoucher.webEcommercesmartvoucher.dto;

import com.smartvoucher.webEcommercesmartvoucher.entity.keys.WarehouseMerchantKeys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseMerchantDTO {

    private WarehouseMerchantKeys keys;
    private long idWarehouse;
    private long idMerchant;
    private String createdBy;
    private String updatedBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String merchantCode;
    private String warehouseCode;
    private String roleCode;


}
