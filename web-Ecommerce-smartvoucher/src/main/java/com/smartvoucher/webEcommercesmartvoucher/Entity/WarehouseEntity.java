package com.smartvoucher.webEcommercesmartvoucher.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class WarehouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // field được references
    @OneToMany(mappedBy = "idWarehouse")
    private List<TicketEntity> tiketEntity;

    @OneToMany(mappedBy = "idWarehouse")
    private List<OrdersEntity> ordersEntity;

}
