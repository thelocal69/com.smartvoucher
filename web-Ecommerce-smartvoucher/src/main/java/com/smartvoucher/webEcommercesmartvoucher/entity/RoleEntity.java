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
@Entity(name = "roles")
public class RoleEntity extends BaseEntity{
    @Column(name = "role_code", unique = true)
    private String roleCode;
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy ="role")
    private List<WareHouseMerchantEntity> wareHouseMerchantEntityList;
}
