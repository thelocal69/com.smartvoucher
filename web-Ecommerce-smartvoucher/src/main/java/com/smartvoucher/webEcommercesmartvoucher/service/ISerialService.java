package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;

public interface ISerialService {
    ResponseObject getAllSerial();
    ResponseObject insertSerial(long idWarehouse, String batchCode, int numberOfSerial);
    ResponseObject updateSerial(SerialDTO serialDTO);
    ResponseObject deleteSerial(long id);
}
