package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.ITicketService;
import com.smartvoucher.webEcommercesmartvoucher.service.impl.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final ITicketService ticketService;

    @Autowired
    public TicketController(ITicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllTicket(){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.ticketService.getAllTicket());
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> insertTicket(@RequestBody @Valid TicketDTO ticketDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.ticketService.insertTicket(ticketDTO));
    }

    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> updateTicket(@RequestBody @Valid TicketDTO ticketDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.ticketService.updateTicket(ticketDTO));
    }

    @DeleteMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteTicket(@RequestParam long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.ticketService.deleteTicket(id));
    }

}
