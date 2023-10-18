package com.smartvoucher.webEcommercesmartvoucher.entity.keys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class Warehouse_SerialKeys implements Serializable {
    @Column(name = "id_warehouse")
    private long idWarehouse;

    @Column(name = "id_serial")
    private long idSerial;

    Warehouse_SerialKeys(){
    }
    Warehouse_SerialKeys(long idWarehouse, long idSerial) {
        this.idWarehouse = idWarehouse;
        this.idSerial = idSerial;
    }

}
