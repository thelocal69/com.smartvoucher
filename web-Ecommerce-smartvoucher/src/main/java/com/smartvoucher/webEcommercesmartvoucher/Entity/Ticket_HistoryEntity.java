package com.smartvoucher.webEcommercesmartvoucher.Entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@Entity(name = "ticket_history")
public class Ticket_HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_ticket")
    @NonNull
    private TicketEntity idTicket;

    @Column(name = "serial_code") // *Noted : xem lại
    private String serialCode;

    @Column(name = "prev_status")
    private int prevStatus;

    @Column(name = "is_latest")
    private int isLatest;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
