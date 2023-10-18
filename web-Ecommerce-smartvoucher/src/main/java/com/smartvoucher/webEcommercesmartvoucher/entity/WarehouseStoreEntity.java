package com.smartvoucher.webEcommercesmartvoucher.entity;

import com.smartvoucher.webEcommercesmartvoucher.entity.keys.Warehouse_StoreKeys;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "warehouse_stores")
public class WarehouseStoreEntity extends BaseEntity implements Serializable {
    @EmbeddedId
    private Warehouse_StoreKeys keys;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "id_warehouse", insertable = false, updatable = false)
    private WareHouseEntity idWarehouse;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "id_store", insertable = false, updatable = false)
    private StoreEntity idStore;

}
