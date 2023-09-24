package com.smartvoucher.webEcommercesmartvoucher.Controller;

import com.smartvoucher.webEcommercesmartvoucher.DTO.Ticket_HistoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.Payload.BaseResponse;
import com.smartvoucher.webEcommercesmartvoucher.Service.Ticket_HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ticket_history")
public class Ticket_HistoryController {

    private Ticket_HistoryService ticketHistoryService;

    @Autowired
    public Ticket_HistoryController(Ticket_HistoryService ticketHistoryService) {
        this.ticketHistoryService = ticketHistoryService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTicketHistory() {

        List<Ticket_HistoryDTO> list = ticketHistoryService.findAllTicketHistory();

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus("200");
        baseResponse.setMessage("List TicketHistory");
        baseResponse.setData(list);

        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }
}
