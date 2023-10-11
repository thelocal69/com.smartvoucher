package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class SerialConverter {

    public List<SerialDTO> findAllSerial(List<SerialEntity> list) {

        List<SerialDTO> listSerial = new ArrayList<>();

        for (SerialEntity data : list) {

            SerialDTO serialDTO = new SerialDTO();
            serialDTO.setId(data.getId());
            serialDTO.setSerialCode(data.getSerialCode());
            serialDTO.setNumberOfSerial(data.getNumberOfSerial());
            serialDTO.setStatus(data.getStatus());
            serialDTO.setBatchCode(data.getBatchCode());
            serialDTO.setCreatedBy(data.getCreatedBy());
            serialDTO.setUpdatedBy(data.getUpdatedBy());
            serialDTO.setCreatedAt(data.getCreatedAt());
            serialDTO.setUpdatedAt(data.getUpdatedAt());

            listSerial.add(serialDTO);
        }

        return listSerial;

    }

    public SerialEntity insertSerial(SerialDTO serialDTO) {

        SerialEntity serialEntity = new SerialEntity();

        serialEntity.setBatchCode(serialDTO.getBatchCode());
        serialEntity.setNumberOfSerial(serialDTO.getNumberOfSerial());
        serialEntity.setSerialCode(serialDTO.getSerialCode());
        // fill default status number 1
        serialEntity.setStatus(1);

        return serialEntity;

    }

    public SerialEntity updateSerial(SerialDTO serialDTO, SerialEntity oldSerial) {

        if ( serialDTO.getBatchCode() != null
                && !serialDTO.getBatchCode().isEmpty()
                && !Objects.equals(serialDTO.getBatchCode(), oldSerial.getBatchCode())) {

            oldSerial.setBatchCode(serialDTO.getBatchCode());
        }

        if (!Objects.equals(serialDTO.getNumberOfSerial(), oldSerial.getNumberOfSerial())) {
            oldSerial.setNumberOfSerial(serialDTO.getNumberOfSerial());
        }

        if (serialDTO.getSerialCode() != null
                && !serialDTO.getSerialCode().isEmpty()
                && !Objects.equals(serialDTO.getSerialCode(), oldSerial.getSerialCode())) {
            oldSerial.setSerialCode(serialDTO.getSerialCode());
        }

        if ( serialDTO.getStatus() > 0
                && !Objects.equals(serialDTO.getStatus(), oldSerial.getStatus())) {
            oldSerial.setStatus(serialDTO.getStatus());
        }

        return oldSerial;
    }

}

