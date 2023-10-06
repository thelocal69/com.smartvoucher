package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.EntityToDTO.Ticket_HistoryEntityToDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.Ticket_HistoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.Ticket_HistoryEntity;
import com.smartvoucher.webEcommercesmartvoucher.repository.Ticket_HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Ticket_HistoryService {

    private Ticket_HistoryRepository ticketHistoryRepository;

    @Autowired
    private Ticket_HistoryEntityToDTO  ticketHistoryEntityToDTO;

    @Autowired
    public Ticket_HistoryService(Ticket_HistoryRepository ticketHistoryRepository) {
        this.ticketHistoryRepository = ticketHistoryRepository;
    }

    public List<Ticket_HistoryDTO> findAllTicketHistory() {

        List<Ticket_HistoryEntity> list = ticketHistoryRepository.findAll();

        List<Ticket_HistoryDTO> listTicketHistory = ticketHistoryEntityToDTO.findAllTicketHistory(list);

        return listTicketHistory;
    }
}
