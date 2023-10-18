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

    // sử dụng để định dạng kểu dữ liệu ' decimal(8, 3) ' trong database (* note : sử dụng trong DTO)
//    private int totalDigits = 8;
//    private int scale = 3;
    //

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
    @JoinColumn(name = "id_serial ")
    @NotNull(message = "Please fill all information!")
    private SerialEntity idSerial;

    @ManyToOne
    @JoinColumn(name = "id_warehouse")
    @NotNull(message = "Please fill all information!")
    private WareHouseEntity idWarehouse;

    @ManyToOne
    @JoinColumn(name = "id_category")
    @NotNull(message = "Please fill all information!")
    private CategoryEntity idCategory;

    @Column(name = "status")
    @NotNull(message = "Please fill all information!")
    private int status;

    @ManyToOne
    @JoinColumn(name = "id_order")
    @NotNull(message = "Please fill all information!")
    private OrderEntity idOrder;

    @Column(name = "claimed_time")
    private Timestamp claimedTime;

    @Column(name = "redeemedtime_time")
    private Timestamp redeemedtimeTime;

    @Column(name = "expired_time")
    private Timestamp expiredTime;

    @Column(name = "discount_type")
    @NotNull(message = "Please fill all information!")
    @NotBlank(message = "Please fill all information!")
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
    @NotNull(message = "Please fill all information!")
    @NotBlank(message = "Please fill all information!")
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
    @NotNull(message = "Please fill all information!")
    @NotBlank(message = "Please fill all information!")
    private String appliedStore;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @NonNull
    private UserEntity idUser;

    // field được references
    @OneToMany(mappedBy = "idTicket")
    private List<TicketHistoryEntity> list;
}
