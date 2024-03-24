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
public class StoreDTO{
    private Long id;
    private String createdBy;
    private Timestamp createdAt;
    private String updatedBy;
    private Timestamp updatedAt;
    private String storeCode;
    @NotBlank(message = "Please fill all data !")
    private String name;
    @NotBlank(message = "Please fill all data !")
    private String address;
    @NotBlank(message = "Please fill all data !")
    private String phone;
    @NotBlank(message = "Please fill all data !")
    private String description;
    @NotNull
    private int status;
    @NotBlank(message = "Merchant name is not empty !")
    @NotNull
    private String merchantName;
    @NotBlank(message = "Chain name is not empty !")
    @NotNull
    private String chainName;
}
