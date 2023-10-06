package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.EntityToDTO.TicketEntityToDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.TicketEntity;
import com.smartvoucher.webEcommercesmartvoucher.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    private TicketEntityToDTO ticketEntityToDTO;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<TicketDTO> findAllTicket() {

        List<TicketEntity> list = ticketRepository.findAll();

        List<TicketDTO> listTicket = ticketEntityToDTO.findAllTicket(list);

        return listTicket;
    }
}
