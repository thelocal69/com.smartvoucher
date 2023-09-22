package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChainDTO extends BaseDTO{
    private String chainCode;
    private String name;
    private String legalName;
    private String logoUrl;
    private String address;
    private String phone;
    private String email;
    private String description;
    private int status;
    private String merchantCode;
}
