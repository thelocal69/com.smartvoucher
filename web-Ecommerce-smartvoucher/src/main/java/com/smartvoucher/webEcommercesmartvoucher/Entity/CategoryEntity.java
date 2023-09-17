package com.smartvoucher.webEcommercesmartvoucher.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "idCategory")
    private List<TicketEntity> tiketEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
