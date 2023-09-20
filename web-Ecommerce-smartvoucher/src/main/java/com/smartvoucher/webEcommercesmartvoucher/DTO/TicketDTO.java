package com.smartvoucher.webEcommercesmartvoucher.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

    private long id;

    private SerialDTO idSerialDTO;

    private WarehouseDTO idWarehouseDTO;

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

    private String appliedStore;

    private UsersDTO idUserDTO;

    private String createdBy;

    private String updatedBy;

    private Timestamp createdAt;

    private Timestamp updatedAt;


}
