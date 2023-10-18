package com.smartvoucher.webEcommercesmartvoucher.entity;

import com.smartvoucher.webEcommercesmartvoucher.entity.keys.Warehouse_MerchantKeys;
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
@Entity(name = "warehouse_merchant")
public class WarehouseMerchantEntity extends BaseEntity implements Serializable {

    @EmbeddedId
    private Warehouse_MerchantKeys keys;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "id_warehouse", insertable = false, updatable = false)
    private WareHouseEntity idWarehouse;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "id_merchant", insertable = false, updatable = false)
    private MerchantEntity idMerchant;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "id_role", insertable = false, updatable = false)
    private RolesEntity idRole;

}
