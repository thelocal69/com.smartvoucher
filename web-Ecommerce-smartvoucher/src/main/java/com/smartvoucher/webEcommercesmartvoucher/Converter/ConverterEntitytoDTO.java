package com.smartvoucher.webEcommercesmartvoucher.Converter;

import com.smartvoucher.webEcommercesmartvoucher.DTO.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.Entity.SerialEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConverterEntitytoDTO {

    public List<SerialDTO> findAllSerial(List<SerialEntity> list) {

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
