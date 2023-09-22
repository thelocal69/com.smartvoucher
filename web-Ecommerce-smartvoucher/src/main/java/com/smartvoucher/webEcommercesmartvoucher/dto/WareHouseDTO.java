package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WareHouseDTO extends BaseDTO{
    private String warehouseCode;
    private String name;
    private String description;
    private String termOfUse;
    private String bannerUrl;
    private String thumbnailUrl;
    private Double discountAmount;
    private Double maxDiscountAmount;
    private Timestamp availableFrom;
    private Timestamp availableTo;
    private int status;
    private int showOnWeb;
    private int capacity;
    private int voucherChannel;
    private String discountTypeCode;
    private String categoryCode;
}
