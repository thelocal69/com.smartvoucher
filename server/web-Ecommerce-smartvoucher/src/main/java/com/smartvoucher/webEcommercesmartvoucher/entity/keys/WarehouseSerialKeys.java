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
public class WarehouseSerialKeys implements Serializable
{
    @Column(name = "id_warehouse")
    private long idWarehouse;

    @Column(name = "id_serial")
    private long idSerial;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WarehouseSerialKeys)) return false;
        WarehouseSerialKeys that = (WarehouseSerialKeys) o;
        return idWarehouse == that.idWarehouse && idSerial == that.idSerial;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idWarehouse, idSerial);
    }
}
