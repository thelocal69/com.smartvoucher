package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.TicketHistoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.BaseResponse;
import com.smartvoucher.webEcommercesmartvoucher.service.impl.TicketHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ticket_history")
public class TicketHistoryController {

    private TicketHistoryService ticketHistoryService;

    @Autowired
    public TicketHistoryController(TicketHistoryService ticketHistoryService) {
        this.ticketHistoryService = ticketHistoryService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTicketHistory() {

        List<TicketHistoryDTO> list = ticketHistoryService.findAllTicketHistory();

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(200);
        baseResponse.setMessage("List TicketHistory");
        baseResponse.setData(list);

        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }
}
