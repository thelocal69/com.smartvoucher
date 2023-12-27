package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;

public interface ISerialService {
    ResponseOutput getAllSerial(int page, int limit, String sortBy, String sortField);
    ResponseObject getAllSerial();
    ResponseObject insertSerial(long idWarehouse, String batchCode, int numberOfSerial);
    ResponseObject updateSerial(SerialDTO serialDTO);
    ResponseObject deleteSerial(long id);
}
