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
public class DiscountTypeDTO extends BaseDTO{
    @NotBlank(message = "Discount type code is not empty !")
    @NotNull
    private String code;
    @NotBlank(message = "Please fill all data !")
    private String name;
    @NotNull
    private int status;
}
