package com.smartvoucher.webEcommercesmartvoucher.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Entity(name = "ticket")
@EntityListeners(AuditingEntityListener.class)
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "created_at")
    @CreatedDate
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "id_serial ", unique = true, nullable = false)
    private SerialEntity idSerial;

    @ManyToOne
    @JoinColumn(name = "id_warehouse", nullable = false)
    private WareHouseEntity idWarehouse;

    @ManyToOne
    @JoinColumn(name = "id_category", unique = true, nullable = false)
    private CategoryEntity idCategory;

    @Column(name = "status", nullable = false)
    private int status;

    @ManyToOne
    @JoinColumn(name = "id_order", unique = true, nullable = false)
    private OrderEntity idOrder;

    @Column(name = "claimed_time")
    private Timestamp claimedTime;

    @Column(name = "redeemedtime_time")
    private Timestamp redeemedtimeTime;

    @Column(name = "expired_time")
    private Timestamp expiredTime;

    @Column(name = "discount_type", nullable = false)
    private String discountType;

    @Column(name = "discount_amount", nullable = false)
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

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity idUser;

    // field được references
    @OneToMany(mappedBy = "idTicket")
    private List<TicketHistoryEntity> list;

    @ManyToOne
    @JoinColumn(name = "applied_store")
    private StoreEntity idStore;
}
