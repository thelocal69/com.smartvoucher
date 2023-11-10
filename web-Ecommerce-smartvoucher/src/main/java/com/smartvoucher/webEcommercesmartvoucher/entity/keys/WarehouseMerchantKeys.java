package com.smartvoucher.webEcommercesmartvoucher.entity.keys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WarehouseMerchantKeys)) return false;
        WarehouseMerchantKeys that = (WarehouseMerchantKeys) o;
        return idWarehouse == that.idWarehouse && idMerchant == that.idMerchant;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idWarehouse, idMerchant);
    }
}
