package com.smartvoucher.webEcommercesmartvoucher.Controller;

import com.smartvoucher.webEcommercesmartvoucher.DTO.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.Payload.BaseResponse;
import com.smartvoucher.webEcommercesmartvoucher.Service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTicket(){

        List<TicketDTO> listTicket = ticketService.findAllTicket();

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus("200");
        baseResponse.setMessage("Danh sách Ticket");
        baseResponse.setData(listTicket);

        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }

}
