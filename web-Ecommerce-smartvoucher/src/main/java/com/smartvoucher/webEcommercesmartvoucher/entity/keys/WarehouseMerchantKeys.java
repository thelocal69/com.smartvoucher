package com.smartvoucher.webEcommercesmartvoucher.entity.keys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class WarehouseMerchantKeys implements Serializable {

    @Column(name = "id_warehouse")
    private long idWarehouse;

    @Column(name = "id_merchant")
    private long idMerchant;

}
