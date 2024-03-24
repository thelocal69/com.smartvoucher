package com.smartvoucher.webEcommercesmartvoucher.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity(name = "serial")
@EntityListeners(AuditingEntityListener.class)
public class SerialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "batch_code", nullable = false)
    private String batchCode;
    @Column(name = "number_of_serial", nullable = false)
    private int numberOfSerial;
    @Column(name = "serial_code", unique = true, nullable = false, length = 10)
    private String serialCode;
    @Column(name = "status", nullable = false)
    private int status;
    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;
    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;
    @Column(name = "created_at")
    @CreatedDate
    private Timestamp createdAt;
    @Column(name = "updated_at")
    @LastModifiedDate
    private Timestamp updatedAt;

    // field được references
    @OneToMany(mappedBy = "idSerial")
    private List<TicketEntity> ticketEntity;
    @OneToMany(mappedBy = "idSerial")
    private List<WarehouseSerialEntity> warehouseSerialEntities;
}
