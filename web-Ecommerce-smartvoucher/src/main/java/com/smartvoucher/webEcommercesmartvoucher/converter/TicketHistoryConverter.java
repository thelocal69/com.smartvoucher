package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.TicketHistoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.TicketEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.TicketHistoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class TicketHistoryConverter {
    private final TicketConverter ticketConverter;

    @Autowired
    public TicketHistoryConverter(TicketConverter ticketConverter) {
        this.ticketConverter = ticketConverter;
    }

    public TicketHistoryDTO toTicketHistoryDTO(TicketHistoryEntity data) {
            TicketHistoryDTO ticketHistoryDTO = new TicketHistoryDTO();
            ticketHistoryDTO.setId(data.getId());
            ticketHistoryDTO.setId(data.getIdTicket().getId());
            ticketHistoryDTO.setIdTicketDTO(ticketConverter.toTicketDTO(data.getIdTicket())); // set id ticketHistory
            ticketHistoryDTO.setSerialCode(data.getSerialCode());
            ticketHistoryDTO.setPrevStatus(data.getPrevStatus());
            ticketHistoryDTO.setIsLatest(data.getIsLatest());
            ticketHistoryDTO.setCreatedBy(data.getCreatedBy());
            ticketHistoryDTO.setUpdatedBy(data.getUpdatedBy());
            ticketHistoryDTO.setCreatedAt(data.getCreatedAt());
            ticketHistoryDTO.setUpdatedAt(data.getUpdatedAt());
        return ticketHistoryDTO;
    }

    public TicketHistoryEntity updateStatusTicketHistory(TicketHistoryEntity oldTicketHistory, int isLatestStatus) {
        if (!Objects.equals(oldTicketHistory.getPrevStatus(), oldTicketHistory.getIsLatest())) {
            oldTicketHistory.setPrevStatus(oldTicketHistory.getIsLatest());
        }
        oldTicketHistory.setIsLatest(isLatestStatus);
        return oldTicketHistory;
    }
}
