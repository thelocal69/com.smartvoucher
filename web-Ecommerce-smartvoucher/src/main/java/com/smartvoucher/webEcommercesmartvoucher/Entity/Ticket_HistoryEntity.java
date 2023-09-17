package com.smartvoucher.webEcommercesmartvoucher.Entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "ticket_history")
public class Ticket_HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


}
