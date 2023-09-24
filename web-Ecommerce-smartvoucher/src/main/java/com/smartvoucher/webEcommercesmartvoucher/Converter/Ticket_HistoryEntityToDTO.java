package com.smartvoucher.webEcommercesmartvoucher.Converter;

import com.smartvoucher.webEcommercesmartvoucher.DTO.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.DTO.Ticket_HistoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.Entity.Ticket_HistoryEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Ticket_HistoryEntityToDTO {

    public List<Ticket_HistoryDTO> findAllTicketHistory(List<Ticket_HistoryEntity> list) {

        List<Ticket_HistoryDTO> listTicketHistory = new ArrayList<>();

        for(Ticket_HistoryEntity data : list) {
            TicketDTO ticketDTO = new TicketDTO();

            Ticket_HistoryDTO ticketHistoryDTO = new Ticket_HistoryDTO();
            ticketHistoryDTO.setId(data.getId());

            ticketDTO.setId(data.getIdTicket().getId());
            ticketHistoryDTO.setIdTicketDTO(ticketDTO); // set id ticketHistory

            ticketHistoryDTO.setSerialCode(data.getSerialCode());
            ticketHistoryDTO.setPrevStatus(data.getPrevStatus());
            ticketHistoryDTO.setIsLatest(data.getIsLatest());
            ticketHistoryDTO.setCreatedBy(data.getCreatedBy());
            ticketHistoryDTO.setUpdatedBy(data.getUpdatedBy());
            ticketHistoryDTO.setCreatedAt(data.getCreatedAt());
            ticketHistoryDTO.setUpdatedAt(data.getUpdatedAt());

            listTicketHistory.add(ticketHistoryDTO);
        }

        return listTicketHistory;
    }
}
