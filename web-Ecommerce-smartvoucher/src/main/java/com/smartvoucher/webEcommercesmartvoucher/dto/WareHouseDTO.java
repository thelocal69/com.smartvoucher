package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WareHouseDTO extends BaseDTO{
    @NotBlank(message = "Warehouse code is not empty !")
    @NotNull
    private String warehouseCode;
    @NotBlank(message = "Please fill all data !")
    private String name;
    @NotBlank(message = "Please fill all data !")
    private String description;
    @NotBlank(message = "Please fill all data !")
    private String termOfUse;
    @NotBlank(message = "Please fill all data !")
    private String bannerUrl;
    @NotBlank(message = "Please fill all data !")
    private String thumbnailUrl;
    @NotNull
    private Double discountAmount;
    @NotNull
    private Double maxDiscountAmount;
    @NotNull
    private Timestamp availableFrom;
    @NotNull
    private Timestamp availableTo;
    @NotNull
    private int status;
    @NotNull
    private int showOnWeb;
    @NotNull
    private int capacity;
    @NotNull
    private int voucherChannel;
    @NotBlank(message = "Discount type code is not empty !")
    @NotNull
    private String discountTypeCode;
    @NotBlank(message = "Category code is not empty !")
    @NotNull
    private String categoryCode;
    @NotBlank(message = "Merchant code is not empty !")
    @NotNull
    private String merchantCode;
    @NotBlank(message = "label name is not empty !")
    @NotNull
    private String labelName;
}
