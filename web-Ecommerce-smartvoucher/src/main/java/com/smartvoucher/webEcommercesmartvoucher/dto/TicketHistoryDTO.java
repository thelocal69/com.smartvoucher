package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketHistoryDTO {

    private long id;

    private TicketDTO idTicketDTO;

    private String serialCode;

    private int prevStatus;

    private int isLatest;

    private String createdBy;

    private String updatedBy;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private long idTicket;
}
