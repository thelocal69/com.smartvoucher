package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;

public interface ISerialService {

    ResponseObject getAllSerial();

    ResponseObject insertSerial(SerialDTO serialDTO);

    ResponseObject updateSerial(SerialDTO serialDTO);

    ResponseObject deleteSerial(long id);
}
