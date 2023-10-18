package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;

public interface ITicketService {

    ResponseObject getAllTicket();

    ResponseObject insertTicket(TicketDTO ticketDTO);
}
