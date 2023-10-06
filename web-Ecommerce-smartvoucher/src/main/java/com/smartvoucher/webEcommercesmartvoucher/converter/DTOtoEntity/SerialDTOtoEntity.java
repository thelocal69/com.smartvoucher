package com.smartvoucher.webEcommercesmartvoucher.converter.DTOtoEntity;

import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import org.springframework.stereotype.Component;

@Component
public class SerialDTOtoEntity {

    public SerialEntity insertSerial(SerialDTO serialDTO) {

        SerialEntity serialEntity = new SerialEntity();

        serialEntity.setBatchCode(serialDTO.getBatchCode());
        serialEntity.setNumberOfSerial(serialDTO.getNumberOfSerial());
        serialEntity.setSerialCode(serialDTO.getSerialCode());
        serialEntity.setCreatedBy(serialDTO.getCreatedBy());
        serialEntity.setUpdatedBy(serialDTO.getUpdatedBy());

        return serialEntity;

    }

}
