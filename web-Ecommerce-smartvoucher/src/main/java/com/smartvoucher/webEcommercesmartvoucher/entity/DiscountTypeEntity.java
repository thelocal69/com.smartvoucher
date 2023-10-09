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
@Entity(name = "discount_type")
public class DiscountTypeEntity extends BaseEntity{
    @Column(name = "code", unique = true)
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "status", nullable = false)
    private int status;

    @OneToMany(mappedBy = "discountType")
    List<WareHouseEntity> wareHouseEntityList;
}
