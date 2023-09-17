package com.smartvoucher.webEcommercesmartvoucher.Entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "orders")
public class OrdersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "idOrder")
    private List<TicketEntity> ticketEntityList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
