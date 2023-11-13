package com.smartvoucher.webEcommercesmartvoucher.service;


import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface ITicketService {
    ResponseObject getAllTicket();
    ResponseObject insertTicket(TicketDTO ticketDTO);
    ResponseObject updateTicket(TicketDTO ticketDTO);
    ResponseObject deleteTicket(long idTicket);
    File uploadTicketImages(MultipartFile fileName);
    TicketDTO getTicketDetail (UserDTO userDTO);
}
