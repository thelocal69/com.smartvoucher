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
public class CategoryDTO extends BaseDTO{
    @NotBlank(message = "Category code is not empty !")
    @NotNull
    private String categoryCode;
    @NotBlank(message = "Please fill all data !")
    private String name;
    @NotNull
    private int status;
    @NotBlank(message = "Please fill all data !")
    private String bannerUrl;
}
