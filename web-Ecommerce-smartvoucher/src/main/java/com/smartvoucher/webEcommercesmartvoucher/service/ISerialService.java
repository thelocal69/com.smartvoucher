package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.payload.BaseResponse;

public interface ISerialService {

    BaseResponse getAllSerial();

    BaseResponse insertSerial(SerialDTO serialDTO);

    BaseResponse updateSerial(SerialDTO serialDTO);

    BaseResponse deleteSerial(long id);
}
