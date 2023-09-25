package com.smartvoucher.webEcommercesmartvoucher.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {

    private long id;

    private String orderNo;

    private int status;

    private UsersDTO idUser;

    private int quantity;

    private WarehouseDTO idWarehouseDTO;

    private String createdBy;

    private String updatedBy;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
