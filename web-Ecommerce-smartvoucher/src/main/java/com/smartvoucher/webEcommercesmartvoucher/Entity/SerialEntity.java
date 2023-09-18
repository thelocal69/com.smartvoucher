package com.smartvoucher.webEcommercesmartvoucher.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity(name = "serial")
public class SerialEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "batch_code")
    private String batch_code;

    @Column(name = "number_of_serial")
    private int numberOfSerial;

    @Column(name = "serial_code")
    private String serialCode;

    @Column(name = "status")
    private int status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    // field được references
    @OneToMany(mappedBy = "idSerial")
    private List<TicketEntity> ticketEntity;

}
