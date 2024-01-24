package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private long id;
    @NotNull(message = "Please fill all information!")
    private long idWarehouse;
    @NotNull(message = "Please fill all information!")
    private long idUser;
    private String orderNo;
    @NotNull(message = "Please fill all information!")
    @Min(value = 1)
    @Max(value = 3)
    private int status;
    private String email;
    @NotNull(message = "Please fill all information!")
    @Min(value = 1)
    private int quantity;
    private double price;
    private String warehouseName;
    private String createdBy;
    private String updatedBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
