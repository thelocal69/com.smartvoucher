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
public class WishListDTO {
    private Long id;
    private Long idUser;
    private Long idWarehouse;
    @NotBlank(message = "Please fill all data !")
    private String name;
    @NotBlank(message = "Please fill all data !")
    private String bannerUrl;
    @NotBlank(message = "Category name is not empty !")
    @NotNull
    private String categoryName;
    private Double price;
    @NotNull
    private Double originalPrice;
    @NotNull
    private Double maxDiscountAmount;
    @NotNull
    private int status;
}
