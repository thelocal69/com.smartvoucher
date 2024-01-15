package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private long id;
    private long idWarehouse;
    private String orderNo;
    @NotNull(message = "Please fill all information!")
    @Min(value = 1)
    @Max(value = 3)
    private int status;
    @NotNull(message = "Please fill all information!")
    private String email;
    @NotNull(message = "Please fill all information!")
    @Min(value = 1)
    private int quantity;
    @NotNull(message = "Please fill all information!")
    private double price;
    @NotNull(message = "Please fill all information!")
    private String warehouseName;
    private String createdBy;
    private String updatedBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
