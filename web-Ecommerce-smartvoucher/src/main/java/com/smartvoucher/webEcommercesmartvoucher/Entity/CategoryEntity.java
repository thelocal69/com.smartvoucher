package com.smartvoucher.webEcommercesmartvoucher.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // field được references
    @OneToMany(mappedBy = "idCategory")
    private List<TicketEntity> tiketEntity;

}
