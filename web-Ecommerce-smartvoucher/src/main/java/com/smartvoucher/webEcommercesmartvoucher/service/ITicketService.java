package com.smartvoucher.webEcommercesmartvoucher.service;


import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import org.springframework.web.multipart.MultipartFile;

public interface ITicketService {
    ResponseObject getAllTicket();
    ResponseObject insertTicket(TicketDTO ticketDTO, String batchCode, int numberOfSerial);
    ResponseObject updateTicket(TicketDTO ticketDTO);
    ResponseObject deleteTicket(long idTicket);
    ResponseObject userUseTicket(String serialCode);
    File uploadTicketImages(MultipartFile fileName);

}
