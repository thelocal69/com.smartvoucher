package com.smartvoucher.webEcommercesmartvoucher.Service;

import com.smartvoucher.webEcommercesmartvoucher.Converter.TicketEntityToDTO;
import com.smartvoucher.webEcommercesmartvoucher.DTO.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.Entity.TicketEntity;
import com.smartvoucher.webEcommercesmartvoucher.Repository.TicketRepository;
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
