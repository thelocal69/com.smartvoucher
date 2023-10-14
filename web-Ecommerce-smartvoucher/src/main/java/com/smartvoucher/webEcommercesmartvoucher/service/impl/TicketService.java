package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.TicketConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.TicketEntity;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.TicketRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService implements ITicketService {

    private final TicketRepository ticketRepository;

    private TicketConverter ticketConverter;

    @Autowired
    public TicketService(TicketRepository ticketRepository, TicketConverter ticketConverter) {
        this.ticketRepository = ticketRepository;
        this.ticketConverter = ticketConverter;
    }


    @Override
    public ResponseObject getAllTicket() {

        List<TicketDTO> listTicket;

        try {

            List<TicketEntity> list = ticketRepository.findAll();
            listTicket = ticketConverter.findAllTicket(list);

        } catch (Exception e) {
            System.out.println("Ticket Service : " + e.getLocalizedMessage());
            return new ResponseObject(500, e.getLocalizedMessage(), "Not found List Ticket !");
        }

        return new ResponseObject(200, "List Ticket", listTicket );

    }
}
