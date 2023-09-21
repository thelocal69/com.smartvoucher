package com.smartvoucher.webEcommercesmartvoucher.entity;

import com.smartvoucher.webEcommercesmartvoucher.entity.keys.WareHouseStoreKeys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "warehouse_serial")
public class WareHouseSerialEntity {
    @EmbeddedId
    private WareHouseStoreKeys wareHouseStoreKeys;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;
    @CreatedDate
    @Column(name = "created_at")
    private Timestamp createdAt;
    @LastModifiedBy
    @Column(name = "update_by")
    private String updatedBy;
    @LastModifiedDate
    @Column(name = "update_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "id_warehouse", insertable = false, updatable = false)
    private WareHouseEntity wareHouse;

    @ManyToOne
    @JoinColumn(name = "id_serial", insertable = false, updatable = false)
    private SerialEntity serial;
}
