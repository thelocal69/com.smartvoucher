package com.smartvoucher.webEcommercesmartvoucher.entity.keys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class Warehouse_MerchantKeys implements Serializable {

    @Column(name = "id_warehouse")
    private long idWarehouse;

    @Column(name = "id_merchant")
    private long idMerchant;

    Warehouse_MerchantKeys(){
    }

    Warehouse_MerchantKeys(long idWarehouse, long idMerchant) {
        this.idWarehouse = idWarehouse;
        this.idMerchant = idMerchant;
    }
}
