package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.ISerialService;
import com.smartvoucher.webEcommercesmartvoucher.service.ITicketService;
import com.smartvoucher.webEcommercesmartvoucher.service.impl.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final ITicketService ticketService;

    @Autowired
    public TicketController(ITicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/api/list-ticket")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllTicket(){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.ticketService.getAllTicket());
    }


    @PostMapping("/api/buy-ticket")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> insertTicket(@RequestBody @Valid TicketDTO ticketDTO, @RequestParam String userEmail) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.ticketService.insertTicket(ticketDTO, userEmail));
    }

    @PutMapping("/api/update-ticket")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> updateTicket(@RequestBody @Valid TicketDTO ticketDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.ticketService.updateTicket(ticketDTO));
    }

    @DeleteMapping("/api/delete-ticket")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteTicket(@RequestParam long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.ticketService.deleteTicket(id));
    }

    @PutMapping("/api/use-ticket")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> userUseVoucher(@RequestParam String serialCode) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.ticketService.userUseTicket(serialCode));
    }

    @PostMapping("/api/upload")
    public ResponseEntity<ResponseObject> uploadFiles(@RequestParam MultipartFile fileName){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Upload images is completed !",
                        ticketService.uploadTicketImages(fileName).getWebViewLink()
                )
        );
    }
    @GetMapping("/api/ticket_detail")
    public ResponseEntity<ResponseObject> getTicketDetail(@RequestBody @Valid UserDTO userDTO){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Ticket detail of user " + userDTO.getUserName() + " is below:",
                        this.ticketService.getTicketDetail(userDTO)
                )
        );
    }

}
