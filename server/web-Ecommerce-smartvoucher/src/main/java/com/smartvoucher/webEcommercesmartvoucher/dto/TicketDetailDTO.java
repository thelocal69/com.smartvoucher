package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDetailDTO {
    private long id;
    @NotNull(message = "Please fill all information!")
    private String  warehouseName;
    @NotNull(message = "Please fill all information!")
    private String categoryName;
    @NotNull(message = "Please fill all information!")
    private String serialCode;
    @NotNull(message = "Please fill all information!")
    @Min(value = 0)
    @Max(value = 3)
    private int status;
    @NotNull(message = "Please fill all information!")
    private String orderNo;
    private Timestamp claimedTime;
    private Timestamp redeemedtimeTime;
    private Timestamp expiredTime;
    @NotNull(message = "Please fill all information!")
    @NotBlank(message = "Please fill all information!")
    private String discountType;
    @NotNull(message = "Please fill all information!")
    private BigDecimal discountAmount;
    private String bannerUrl;
    private String thumbnailUrl;
    private String acquirerLogoUrl;
    private String termOfUse;
    private String description;
    private String voucherChannel;
    private Timestamp availableFrom;
    private Timestamp availableTo;
    @NotNull(message = "Please fill all information!")
    private String storeName;
    @NotNull(message = "Please fill all information!")
    private String email;
    private String createdBy;
    private String updatedBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
