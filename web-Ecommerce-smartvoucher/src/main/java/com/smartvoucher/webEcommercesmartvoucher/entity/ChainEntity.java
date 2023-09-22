package com.smartvoucher.webEcommercesmartvoucher.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "chains")
public class ChainEntity extends BaseEntity{
    @Column(name = "chain_code", unique = true)
    private String chainCode;
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

    @ManyToOne
    @JoinColumn(name = "id_merchant")
    private MerchantEntity merchant;

    @OneToMany(mappedBy = "chain")
    private List<StoreEntity> storeEntityList;
}
