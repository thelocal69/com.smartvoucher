package com.smartvoucher.webEcommercesmartvoucher.service;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import org.springframework.web.multipart.MultipartFile;

public interface ITicketService {

    ResponseObject getAllTicket();
    File uploadTicketImages(MultipartFile fileName);
}
