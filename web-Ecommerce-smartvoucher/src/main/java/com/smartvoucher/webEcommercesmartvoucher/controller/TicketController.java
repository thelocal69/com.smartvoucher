package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.ITicketService;
import com.smartvoucher.webEcommercesmartvoucher.service.impl.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final ITicketService ticketService;

    public TicketController(ITicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTicket(){
        ResponseObject responseObject = ticketService.getAllTicket();
        return ResponseEntity.status(responseObject.getStatusCode()).body(responseObject);
    }

    @PostMapping
    public ResponseEntity<?> insertTicket(@RequestBody TicketDTO ticketDTO) {
        ResponseObject responseObject = ticketService.insertTicket(ticketDTO);
        return ResponseEntity.status(responseObject.getStatusCode()).body(responseObject);
    }

}
