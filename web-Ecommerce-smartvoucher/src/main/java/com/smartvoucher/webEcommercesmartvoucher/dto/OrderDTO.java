package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private long id;
    @NotNull(message = "Please fill all information!")
    @NotBlank(message = "Please fill all information!")
    private String orderNo;
    @NotNull(message = "Please fill all information!")
    @Min(value = 1)
    @Max(value = 3)
    private int status;
    @NotNull(message = "Please fill all information!")
    private UserDTO idUserDTO;
    @NotNull(message = "Please fill all information!")
    @Min(value = 1)
    private int quantity;
    @NotNull(message = "Please fill all information!")
    private WareHouseDTO idWarehouseDTO;
    private String createdBy;
    private String updatedBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
