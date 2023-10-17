package com.smartvoucher.webEcommercesmartvoucher.entity.keys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class Warehouse_StoreKeys implements Serializable {

    @Column(name = "id_warehouse")
    private long idWarehouse;

    @Column(name = "id_store")
    private long idStore;

    Warehouse_StoreKeys(){
    }

    Warehouse_StoreKeys(long idWarehouse, long idStore) {
        this.idWarehouse = idWarehouse;
        this.idStore = idStore;
    }
}
