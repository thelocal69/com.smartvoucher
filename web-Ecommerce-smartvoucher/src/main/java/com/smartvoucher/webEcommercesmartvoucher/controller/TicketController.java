package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.service.ITicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;


@Slf4j
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

    @GetMapping("/api/get/all")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseOutput> getAllTicketByOrder(
            @RequestParam long id,
            @RequestParam int page,
            @RequestParam int limit
    ){
        return new ResponseEntity<>(this.ticketService.getAllTicket(
                id, page, limit), HttpStatus.OK);
    }


    @PostMapping("/api/buy-ticket")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> insertTicket(@RequestBody @Valid TicketDTO ticketDTO,
                                          @RequestParam String userEmail,
                                          @RequestParam int numberOfSerial) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.ticketService.insertTicket(ticketDTO, userEmail, numberOfSerial));
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
        log.info("Upload images is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Upload images is completed !",
                        ticketService.uploadTicketImages(fileName)
                )
        );
    }
    @GetMapping("/api/ticket_detail")
    public ResponseEntity<ResponseObject> getTicketDetail(@RequestBody @Valid UserDTO userDTO){
        log.info("Ticket detail of user " + userDTO.getUserName() + " is below:");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Ticket detail of user " + userDTO.getUserName() + " is below:",
                        this.ticketService.getTicketDetail(userDTO)
                )
        );
    }

}
