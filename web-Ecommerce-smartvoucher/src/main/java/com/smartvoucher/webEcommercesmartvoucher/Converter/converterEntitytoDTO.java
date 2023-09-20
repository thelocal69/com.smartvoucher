package com.smartvoucher.webEcommercesmartvoucher.Converter;

import com.smartvoucher.webEcommercesmartvoucher.DTO.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.DTO.UsersDTO;
import com.smartvoucher.webEcommercesmartvoucher.Entity.SerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.Entity.UsersEntity;
import com.smartvoucher.webEcommercesmartvoucher.Service.SerialService;

import java.util.ArrayList;
import java.util.List;

public class converterEntitytoDTO {

    private SerialService serialService;


    public List<SerialDTO> findAllSerial() {
        List<SerialEntity> list = serialService.findAllSerial();
        List<SerialDTO> listSerial = new ArrayList<>();

        for (SerialEntity data : list) {
            SerialDTO serialDTO = new SerialDTO();
            serialDTO.setId(data.getId());
            serialDTO.setSerialCode(data.getSerialCode());
            serialDTO.setNumberOfSerial(data.getNumberOfSerial());
            serialDTO.setStatus(data.getStatus());
            serialDTO.setBatchCode(data.getBatch_code());
            serialDTO.setCreatedBy(data.getCreatedBy());
            serialDTO.setUpdatedBy(data.getUpdatedBy());
            serialDTO.setCreatedAt(data.getCreatedAt());
            serialDTO.setUpdatedAt(data.getUpdatedAt());

            listSerial.add(serialDTO);
        }

        return listSerial;

    }
}
