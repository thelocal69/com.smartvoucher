package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private long id;
    private SerialDTO idSerialDTO;
    @NotNull(message = "Please fill all information!")
    private WareHouseDTO idWarehouseDTO;
    @NotNull(message = "Please fill all information!")
    private CategoryDTO idCategoryDTO;
    @NotNull(message = "Please fill all information!")
    @Min(value = 0)
    @Max(value = 3)
    private int status;
    @NotNull(message = "Please fill all information!")
    private OrderDTO idOrderDTO;
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
    private StoreDTO idStoreDTO;
    @NotNull(message = "Please fill all information!")
    private UserDTO idUserDTO;

    private String createdBy;

    private String updatedBy;

    private Timestamp createdAt;

    private Timestamp updatedAt;
    private int numberOfSerial;
}
