
package com.smartvoucher.webEcommercesmartvoucher.dto;

import com.smartvoucher.webEcommercesmartvoucher.entity.keys.WarehouseSerialKeys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseSerialDTO {
    private WarehouseSerialKeys keys;
    private long idWarehouse;
    private long idSerial;
    private String createdBy;
    private String updatedBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String warehouseCode;
    private String serialCode;

}
