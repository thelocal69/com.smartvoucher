package com.smartvoucher.webEcommercesmartvoucher.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity(name = "store")
    public class StoreEntity extends BaseEntity{
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
    }
