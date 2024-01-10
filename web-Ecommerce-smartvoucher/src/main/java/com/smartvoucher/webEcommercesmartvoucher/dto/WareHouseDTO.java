package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WareHouseDTO{
    private Long id;
    private Long idCategory;
    private Long idDiscountType;
    private String createdBy;
    private Timestamp createdAt;
    private String updatedBy;
    private Timestamp updatedAt;
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
    private Double price;
    @NotNull
    private Double originalPrice;
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
    @NotBlank(message = "Discount type name is not empty !")
    @NotNull
    private String discountTypeName;
    @NotBlank(message = "Category name is not empty !")
    @NotNull
    private String categoryName;
    @NotBlank(message = "label name is not empty !")
    @NotNull
    private String labelName;
}
