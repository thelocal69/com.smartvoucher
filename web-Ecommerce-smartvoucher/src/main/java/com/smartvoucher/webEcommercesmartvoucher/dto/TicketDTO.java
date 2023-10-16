package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

    private long id;

    private SerialDTO idSerialDTO;

    private WareHouseDTO idWarehouseDTO;

    private CategoryDTO idCategoryDTO;

    private int status;

    private OrdersDTO idOrderDTO;

    private Timestamp claimedTime;

    private Timestamp redeemedtimeTime;

    private Timestamp expiredTime;

    private String discountType;

    private BigDecimal discountAmount;

    private String bannerUrl;

    private String thumbnailUrl;

    private String acquirerLogoUrl;

    private String termOfUse;

    private String description;

    private String voucherChannel;

    private Timestamp availableFrom;

    private Timestamp availableTo;

    private StoreDTO storeDTO;

    private String appliedStore;

    private UsersDTO idUserDTO;

    private String createdBy;

    private String updatedBy;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    // getter spectify return id
    public long getIdWarehouseDTO() {
        return this.idWarehouseDTO.getId();
    }

    public long getIdSerialDTO() {
        return this.idSerialDTO.getId();
    }

    public long getIdCategoryDTO() {
        return this.idCategoryDTO.getId();
    }

    public long getIdOrderDTO() {
        return this.idOrderDTO.getId();
    }

    public long getIdUserDTO() {
        return this.idUserDTO.getId();
    }
}
