package com.smartvoucher.webEcommercesmartvoucher.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "merchant")
public class MerchantEntity extends BaseEntity{
    @Column(name = "merchant_code", unique = true)
    private String merchantCode;
    @Column(name = "name")
    private String name;
    @Column(name = "legal_name")
    private String legalName;
    @Column(name = "logo_url")
    private String logoUrl;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "description")
    private String description;
    @Column(name = "status", nullable = false)
    private int status;

    @OneToMany(mappedBy = "merchant")
    private List<ChainEntity> chainEntityList;

    @OneToMany(mappedBy = "merchant")
    private List<StoreEntity> storeEntityList;

    @OneToMany(mappedBy = "merchant")
    private List<WareHouseEntity> wareHouseEntityList;
}
