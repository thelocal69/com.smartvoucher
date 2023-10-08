package com.smartvoucher.webEcommercesmartvoucher.converter.entityToDTO;

import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SerialEntityToDTO {

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
}
