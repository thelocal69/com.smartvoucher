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
public class WarehouseStoreKeys implements Serializable {

    @Column(name = "id_warehouse")
    private long idWarehouse;

    @Column(name = "id_store")
    private long idStore;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WarehouseStoreKeys)) return false;
        WarehouseStoreKeys that = (WarehouseStoreKeys) o;
        return idWarehouse == that.idWarehouse && idStore == that.idStore;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idWarehouse, idStore);
    }
}
