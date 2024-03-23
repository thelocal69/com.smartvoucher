package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuyTicketDTO {
    private long idSerial;
    private long idCategory;
    private long idWarehouse;
    private long idOrder;
    private long idStore;
    private long idUser;
    private Timestamp claimedTime;
    private Timestamp redeemTime;
    private Timestamp expiredTime;
    private String discountType;
    private BigDecimal discountAmount;
    private String bannerUrl;
    private Timestamp availableFrom;
    private Timestamp availableTo;
    private int status;
    private int numberOfSerial;
    private String email;
}
