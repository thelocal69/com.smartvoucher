package com.smartvoucher.webEcommercesmartvoucher.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@Entity(name = "ticket_history")
@EntityListeners(AuditingEntityListener.class)
public class TicketHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_ticket")
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
