package com.smartvoucher.webEcommercesmartvoucher.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "warehouse")
public class WareHouseEntity extends BaseEntity{
    @Column(name = "warehouse_code", unique = true)
    private String warehouseCode;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "term_of_use")
    private String termOfUse;
    @Column(name = "banner_url")
    private String bannerUrl;
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;
    @Column(name = "discount_amount")
    private Double discountAmount;
    @Column(name = "max_discount_amount")
    private Double maxDiscountAmount;
    @Column(name = "available_from")
    private Timestamp availableFrom;
    @Column(name = "available_to")
    private Timestamp availableTo;
    @Column(name = "status", nullable = false)
    private int status;
    @Column(name = "show_on_web", nullable = false)
    private int showOnWeb;
    @Column(name = "capacity")
    private int capacity;
    @Column(name = "voucher_channel", nullable = false)
    private int voucherChannel;

    @ManyToOne
    @JoinColumn(name = "id_discount_type")
    private DiscountTypeEntity discountType;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private CategoryEntity category;

    // field được references
    @OneToMany(mappedBy = "idWarehouse")
    private List<OrderEntity> ordersEntities;

    @OneToMany(mappedBy = "idWarehouse")
    private List<TicketEntity> ticketEntities;

    @OneToMany(mappedBy = "idWarehouse")
    private List<WarehouseSerialEntity> warehouseSerialEntities;

    @OneToMany(mappedBy = "idWarehouse")
    private List<WarehouseStoreEntity> warehouseStoreEntities;

    @ManyToOne
    @JoinColumn(name = "id_label")
    private LabelEntity label;
}
