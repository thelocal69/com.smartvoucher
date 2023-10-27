package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.TicketHistoryConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.TicketHistoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.TicketHistoryEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.TicketHistoryRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.ITicketHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketHistoryService implements ITicketHistoryService {

    private final TicketHistoryRepository ticketHistoryRepository;
    private final TicketHistoryConverter ticketHistoryConverter;

    @Autowired
    public TicketHistoryService(TicketHistoryRepository ticketHistoryRepository,
                                TicketHistoryConverter ticketHistoryConverter) {
        this.ticketHistoryRepository = ticketHistoryRepository;
        this.ticketHistoryConverter = ticketHistoryConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseObject getAllTicketHistory(){
        List<TicketHistoryDTO> listTicketHistory = new ArrayList<>();
        List<TicketHistoryEntity> list = ticketHistoryRepository.findAll();
        if (!list.isEmpty()) {
            for (TicketHistoryEntity data : list) {
                listTicketHistory.add( ticketHistoryConverter.toTicketHistoryDTO(data));
            }
            return new ResponseObject(200, "List Ticket History", listTicketHistory);
        } else {
            throw new ObjectEmptyException(406, "List TicketHistory is empty");
        }

    }
}
