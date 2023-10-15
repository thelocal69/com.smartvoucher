package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.TicketHistoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.TicketEntity;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.ITicketHistoryService;
import com.smartvoucher.webEcommercesmartvoucher.service.impl.TicketHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ticket_history")
public class TicketHistoryController {

    private ITicketHistoryService ticketHistoryService;

    @Autowired
    public TicketHistoryController(ITicketHistoryService ticketHistoryService) {
        this.ticketHistoryService = ticketHistoryService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTicketHistory() throws Exception {

        ResponseObject responseObject = ticketHistoryService.getAllTicketHistory();

        return ResponseEntity.status(responseObject.getStatusCode()).body(responseObject);
    }
}
