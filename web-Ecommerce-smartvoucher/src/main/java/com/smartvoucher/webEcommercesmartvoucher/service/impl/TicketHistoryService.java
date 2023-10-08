package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.entityToDTO.TicketHistoryEntityToDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.TicketHistoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.TicketHistoryEntity;
import com.smartvoucher.webEcommercesmartvoucher.repository.TicketHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketHistoryService {

    private TicketHistoryRepository ticketHistoryRepository;

    @Autowired
    private TicketHistoryEntityToDTO ticketHistoryEntityToDTO;

    @Autowired
    public TicketHistoryService(TicketHistoryRepository ticketHistoryRepository) {
        this.ticketHistoryRepository = ticketHistoryRepository;
    }

    public List<TicketHistoryDTO> findAllTicketHistory() {

        List<TicketHistoryEntity> list = ticketHistoryRepository.findAll();

        List<TicketHistoryDTO> listTicketHistory = ticketHistoryEntityToDTO.findAllTicketHistory(list);

        return listTicketHistory;
    }
}
