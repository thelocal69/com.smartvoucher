package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.service.ITicketHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket_history")
public class TicketHistoryController {
    private final ITicketHistoryService ticketHistoryService;

    @Autowired
    public TicketHistoryController(ITicketHistoryService ticketHistoryService) {
        this.ticketHistoryService = ticketHistoryService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllTicketHistory(){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.ticketHistoryService.getAllTicketHistory());
    }
}
