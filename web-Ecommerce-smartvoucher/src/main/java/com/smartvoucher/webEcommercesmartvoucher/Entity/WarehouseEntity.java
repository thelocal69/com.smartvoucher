package com.smartvoucher.webEcommercesmartvoucher.Entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
public class WarehouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "idWarehouse")
    private List<TicketEntity> tiketEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
