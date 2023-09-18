package com.smartvoucher.webEcommercesmartvoucher.Entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Entity(name = "orders")
public class OrdersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "status")
    private int status;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @NonNull
    private UsersEntity idUser;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "id_warehouse")
    @NonNull
    private WarehouseEntity idWarehouse;

    // field được references
    @OneToMany(mappedBy = "idOrder")
    private List<TicketEntity> ticketEntityList;

}
