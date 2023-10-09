package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.TicketHistoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.TicketHistoryEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TicketHistoryConverter {

    public List<TicketHistoryDTO> findAllTicketHistory(List<TicketHistoryEntity> list) {

        List<TicketHistoryDTO> listTicketHistory = new ArrayList<>();

        for(TicketHistoryEntity data : list) {
            TicketDTO ticketDTO = new TicketDTO();

            TicketHistoryDTO ticketHistoryDTO = new TicketHistoryDTO();
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
