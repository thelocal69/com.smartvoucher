package com.smartvoucher.webEcommercesmartvoucher.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Entity(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class OrdersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    @Column(name = "order_no")
    @NotNull(message = "Please fill all information!")
    @NotBlank(message = "Please fill all information!")
    private String orderNo;

    @Column(name = "status")
    @NotNull(message = "Please fill all information!")
    private int status;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @NotNull(message = "Please fill all information!")
    private UsersEntity idUser;

    @Column(name = "quantity")
    @NotNull(message = "Please fill all information!")
    @Min(value = 1 , message = "Please fill all information and minimum quantity is 1! ")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "id_warehouse")
    @NotNull(message = "Please fill all information!")
    private WareHouseEntity idWarehouse;

    // field được references
    @OneToMany(mappedBy = "idOrder")
    private List<TicketEntity> ticketEntityList;

}
