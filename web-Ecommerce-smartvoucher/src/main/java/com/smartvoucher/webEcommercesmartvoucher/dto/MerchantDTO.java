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
public class
MerchantDTO{
    private Long id;
    private String createdBy;
    private Timestamp createdAt;
    private String updatedBy;
    private Timestamp updatedAt;
    private String merchantCode;
    @NotBlank(message = "Please fill all data !")
    private String name;
    @NotBlank(message = "Please fill all data !")
    private String legalName;
    @NotBlank(message = "Please fill all data !")
    private String logoUrl;
    @NotBlank(message = "Please fill all data !")
    private String address;
    @NotBlank(message = "Please fill all data !")
    private String phone;
    @NotBlank(message = "Please fill all data !")
    private String email;
    @NotBlank(message = "Please fill all data !")
    private String description;
    @NotNull
    private int status;
}
