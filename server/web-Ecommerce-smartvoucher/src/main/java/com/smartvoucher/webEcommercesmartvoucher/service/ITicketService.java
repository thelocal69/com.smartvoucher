package com.smartvoucher.webEcommercesmartvoucher.service;


import com.smartvoucher.webEcommercesmartvoucher.dto.BuyTicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface ITicketService {
    ResponseObject getAllTicket();
    ResponseOutput getAllTicket(long id, int page, int limit);
    ResponseObject insertTicket(BuyTicketDTO buyTicketDTO) throws MessagingException, UnsupportedEncodingException;
    ResponseObject updateTicket(TicketDTO ticketDTO);
    ResponseObject deleteTicket(long idTicket);
    String userUseTicket(String serialCode);
    TicketDTO getTicketDetail (UserDTO userDTO);
}
