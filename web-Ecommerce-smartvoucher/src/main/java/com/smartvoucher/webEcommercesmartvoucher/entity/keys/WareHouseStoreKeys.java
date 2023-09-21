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
public class WareHouseStoreKeys implements Serializable {
    @Column(name = "id_warehouse")
    private Long idWareHouse;
    @Column(name = "id_store")
    private Long idStore;
}
