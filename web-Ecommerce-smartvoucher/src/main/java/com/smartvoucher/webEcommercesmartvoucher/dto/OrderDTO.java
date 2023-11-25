package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private long id;
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
    private List<TicketDTO> listTicketDTO;
    private String discountName;
    private Long idStore;
    private Long idUser;
    private Long idWarehouse;
    private String createdBy;
    private String updatedBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
