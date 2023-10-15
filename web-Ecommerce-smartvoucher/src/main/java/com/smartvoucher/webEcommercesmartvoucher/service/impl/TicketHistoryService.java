package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.TicketHistoryConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.TicketHistoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.RolesEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.TicketEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.TicketHistoryEntity;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.TicketHistoryRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.TicketRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.ITicketHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketHistoryService implements ITicketHistoryService {

    private TicketHistoryRepository ticketHistoryRepository;
    private TicketRepository ticketRepository;

    @Autowired
    private TicketHistoryConverter ticketHistoryConverter;

    @Autowired
    public TicketHistoryService(TicketHistoryRepository ticketHistoryRepository, TicketRepository ticketRepository) {
        this.ticketHistoryRepository = ticketHistoryRepository;
        this.ticketRepository = ticketRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public ResponseObject getAllTicketHistory() throws Exception{

        List<TicketHistoryDTO> listTicketHistory;

        try {
            List<TicketHistoryEntity> list = ticketHistoryRepository.findAll();

            listTicketHistory = ticketHistoryConverter.findAllTicketHistory(list);

        } catch (Exception e) {
            System.out.println("TicketHistory Service : " + e.getLocalizedMessage());
            return new ResponseObject(500, e.getLocalizedMessage(), "Not found List Ticket History  !");
        }

        return new ResponseObject(200, "List Ticket History", listTicketHistory);
    }

    @Override
    public void addTicketHistory(TicketEntity ticketEntity) throws Exception {

        boolean isSuccess = false;

        Optional<TicketEntity> ticket = ticketRepository.findById(ticketEntity.getId());
        Optional<TicketHistoryEntity> ticketHistory = ticketHistoryRepository.findBySerialCode(ticketEntity.getIdSerial().getSerialCode());

        if (ticketHistory.isEmpty() && !ticket.isEmpty()) {

            try {

                ticketHistoryRepository.save(ticketHistoryConverter.insertTicketHistory(ticket.orElse(null)));
                isSuccess = true;

            } catch (javax.validation.ConstraintViolationException ex) {
                throw new javax.validation.ConstraintViolationException("Validation Fail!", ex.getConstraintViolations());

            } catch (Exception e) {
                System.out.println("Ticket History service : " + e.getLocalizedMessage() );
            }
        }

        String message = (isSuccess == true) ? "Add Ticket History success!":"Add Ticket History fail!";
        System.out.println(message);
    }
}
