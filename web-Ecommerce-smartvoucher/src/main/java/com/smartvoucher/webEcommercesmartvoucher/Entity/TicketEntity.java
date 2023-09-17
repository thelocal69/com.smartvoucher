package com.smartvoucher.webEcommercesmartvoucher.Entity;

import lombok.Generated;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Setter
@Getter
@Entity(name = "ticket")
public class TicketEntity {

    // sử dụng để định dạng kểu dữ liệu ' decimal(8, 3) ' trong database (* note : sử dụng trong DTO)
    private int totalDigits = 8;
    private int scale = 3;
    //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "id_serial ")
    @NonNull
    private SerialEntity idSerial;

    @ManyToOne
    @JoinColumn(name = "id_warehouse")
    @NonNull
    private WarehouseEntity idWarehouse;

    @ManyToOne
    @JoinColumn(name = "id_category")
    @NonNull
    private CategoryEntity idCategory;

    @Column(name = "status")
    private int status;

    @ManyToOne
    @JoinColumn(name = "id_order")
    @NonNull
    private OrdersEntity idOrder;

    @Column(name = "claimed_time")
    private Timestamp claimedTime;

    @Column(name = "redeemedtime_time")
    private Timestamp redeemedtimeTime;

    @Column(name = "expired_time")
    private Timestamp expiredTime;

    @Column(name = "discount_type")
    private String discountType;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    @Column(name = "banner_url")
    private String bannerUrl;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "acquirer_logo_url")
    private String acquirerLogoUrl;

    @Column(name = "term_of_use")
    private String termOfUse;

    @Column(name = "description")
    private String description;
    @Column(name = "vouncher_channel")
    private String voucherChannel;

    @Column(name = "available_from")
    private Timestamp availbleFrom;

    @Column(name = "available_to")
    private Timestamp avaibleTo;

    @Column(name = "applied_store")
    private String appliedStore;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @NonNull
    private UsersEntity idUser;


}
