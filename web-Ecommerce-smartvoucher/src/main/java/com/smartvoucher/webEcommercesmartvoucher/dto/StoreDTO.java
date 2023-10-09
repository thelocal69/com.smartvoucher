package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO extends BaseDTO{
    private String storeCode;
    private String name;
    private String address;
    private String phone;
    private String description;
    private int status;
    private String merchantCode;
    private String chainCode;
}
