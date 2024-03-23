package com.smartvoucher.webEcommercesmartvoucher.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@Entity(name = "ticket_history")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class  TicketHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_ticket")
    private TicketEntity idTicket;

    @Column(name = "serial_code")
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

    public TicketHistoryEntity(final TicketEntity idTicket,
                               final int isLatest,
                               final int prevStatus,
                               final String serialCode) {
        this.idTicket = idTicket;
        this.isLatest = isLatest;
        this.prevStatus = prevStatus;
        this.serialCode = serialCode;
    }
}
