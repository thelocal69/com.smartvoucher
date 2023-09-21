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
@Entity(name = "serial")
public class SerialEntity extends BaseEntity{
    @Column(name = "batch_code", unique = true)
    private String batchCode;
    @Column(name = "number_of_serial")
    private int numberOfSerial;
    @Column(name = "serial_code", unique = true)
    private String serialCode;
    @Column(name = "status", nullable = false)
    private int status;

    @OneToMany(mappedBy = "serial")
    private List<WareHouseSerialEntity> wareHouseSerialEntityList;
}
