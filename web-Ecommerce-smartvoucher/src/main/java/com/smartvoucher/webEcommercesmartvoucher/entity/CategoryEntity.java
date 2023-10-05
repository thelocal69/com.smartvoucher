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
@Entity(name = "category")
public class CategoryEntity extends BaseEntity{
    @Column(name = "category_code", unique = true)
    private String categoryCode;
    @Column(name = "name")
    private String name;
    @Column(name = "status", nullable = false)
    private int status;

    @OneToMany(mappedBy = "category")
    private List<WareHouseEntity> wareHouseEntityList;
}
