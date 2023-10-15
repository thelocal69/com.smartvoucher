package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;

public interface ISerialService {

    ResponseObject getAllSerial() throws Exception;

    ResponseObject insertSerial(SerialDTO serialDTO) throws Exception;

    ResponseObject updateSerial(SerialDTO serialDTO) throws Exception;

    ResponseObject deleteSerial(long id) throws Exception;
}
