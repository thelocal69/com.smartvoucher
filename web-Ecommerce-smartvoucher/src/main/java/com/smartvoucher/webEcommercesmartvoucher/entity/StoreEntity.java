package com.smartvoucher.webEcommercesmartvoucher.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "store")
public class StoreEntity{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @CreatedBy
        @Column(name = "created_by")
        private String createdBy;
        @CreatedDate
        @Column(name = "created_at")
        private Timestamp createdAt;
        @LastModifiedBy
        @Column(name = "updated_by")
        private String updateBy;
        @LastModifiedDate
        @Column(name = "updated_at")
        private Timestamp updateAt;
        @Column(name = "store_code", unique = true)
        private String storeCode;
        @Column(name = "name")
        private String name;
        @Column(name = "address")
        private String address;
        @Column(name = "phone")
        private String phone;
        @Column(name = "description")
        private String description;
        @Column(name = "status", nullable = false)
        private int status;

        @ManyToOne
        @JoinColumn(name = "id_merchant")
        private MerchantEntity merchant;

        @ManyToOne
        @JoinColumn(name = "id_chain")
        private ChainEntity chain;

        @OneToMany(mappedBy = "idStore")
        private List<TicketEntity> ticketEntityList;

        @OneToMany(mappedBy = "idStore")
        private List<WarehouseStoreEntity> warehouseStoreEntities;
    }
