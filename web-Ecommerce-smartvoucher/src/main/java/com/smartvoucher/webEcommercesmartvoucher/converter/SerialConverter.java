package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.util.RandomCodeHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class SerialConverter {

    private final RandomCodeHandler randomCodeHandler;

    public SerialConverter(RandomCodeHandler randomCodeHandler){
        this.randomCodeHandler = randomCodeHandler;
    }

    public SerialDTO toSerialDTO(SerialEntity serialEntity) {
            SerialDTO serialDTO = new SerialDTO();
            serialDTO.setId(serialEntity.getId());
            serialDTO.setSerialCode(serialEntity.getSerialCode());
            serialDTO.setNumberOfSerial(serialEntity.getNumberOfSerial());
            serialDTO.setStatus(serialEntity.getStatus());
            serialDTO.setBatchCode(serialEntity.getBatchCode());
            serialDTO.setCreatedBy(serialEntity.getCreatedBy());
            serialDTO.setUpdatedBy(serialEntity.getUpdatedBy());
            serialDTO.setCreatedAt(serialEntity.getCreatedAt());
            serialDTO.setUpdatedAt(serialEntity.getUpdatedAt());
        return serialDTO;
    }

    public SerialEntity toSerialEntity(SerialDTO serialDTO) {
            SerialEntity serialEntity = new SerialEntity();
            serialEntity.setBatchCode(serialDTO.getBatchCode());
            serialEntity.setNumberOfSerial(serialDTO.getNumberOfSerial());
            serialEntity.setSerialCode(randomCodeHandler.generateRandomChars(10));
            serialEntity.setStatus(1);
        return serialEntity;
    }

    public List<SerialDTO> toSerialDTOList(List<SerialEntity> serialEntityList){
        return serialEntityList.stream().map(this::toSerialDTO).collect(Collectors.toList());
    }

    public SerialEntity generateSerial(String batchCode, int numberOfSerial, String serialCode ) {
        SerialEntity serialEntity = new SerialEntity();
        serialEntity.setBatchCode(batchCode);
        serialEntity.setNumberOfSerial(numberOfSerial);
        serialEntity.setSerialCode(serialCode);
        serialEntity.setStatus(1);
        return serialEntity;
    }

    public SerialEntity updateSerial(SerialDTO serialDTO, SerialEntity oldSerial) {
            if (!Objects.equals(serialDTO.getNumberOfSerial(), oldSerial.getNumberOfSerial())) {
                oldSerial.setNumberOfSerial(serialDTO.getNumberOfSerial());
            }
            if (!Objects.equals(serialDTO.getStatus(), oldSerial.getStatus())) {
                oldSerial.setStatus(serialDTO.getStatus());
            }
        return oldSerial;
    }

}

