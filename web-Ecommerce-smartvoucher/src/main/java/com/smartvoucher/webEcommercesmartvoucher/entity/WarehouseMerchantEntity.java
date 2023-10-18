package com.smartvoucher.webEcommercesmartvoucher.entity;

import com.smartvoucher.webEcommercesmartvoucher.entity.keys.WarehouseMerchantKeys;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "warehouse_merchant")
public class WarehouseMerchantEntity{

    @EmbeddedId
    private WarehouseMerchantKeys keys;
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;
    @CreatedDate
    @Column(name = "created_at")
    private Timestamp createdAt;
    @LastModifiedBy
    @Column(name = "updated_by")
    private String updateBy;
    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updateAt;

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
