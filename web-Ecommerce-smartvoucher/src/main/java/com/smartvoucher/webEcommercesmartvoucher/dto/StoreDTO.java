package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO extends BaseDTO{
    @NotBlank(message = "Store code is not empty !")
    @NotNull
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
    @NotBlank(message = "Merchant code is not empty !")
    @NotNull
    private String merchantCode;
    @NotBlank(message = "Chain code is not empty !")
    @NotNull
    private String chainCode;
}
