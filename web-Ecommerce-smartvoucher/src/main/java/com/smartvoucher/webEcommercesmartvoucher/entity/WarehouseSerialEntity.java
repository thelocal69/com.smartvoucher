package com.smartvoucher.webEcommercesmartvoucher.entity;

import com.smartvoucher.webEcommercesmartvoucher.entity.keys.Warehouse_SerialKeys;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "warehouse_serial")
public class WarehouseSerialEntity extends BaseEntity implements Serializable {
    @EmbeddedId
    private Warehouse_SerialKeys keys;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "id_warehouse", updatable = false, insertable = false)
    private WareHouseEntity idWarehouse;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "id_serial", updatable = false, insertable = false)
    private SerialEntity idSerial;
}
