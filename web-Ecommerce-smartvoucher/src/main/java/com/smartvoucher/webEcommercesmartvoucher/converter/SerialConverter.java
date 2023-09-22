package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SerialConverter {
    public SerialDTO toSerialDTO(SerialEntity serialEntity){
        SerialDTO serialDTO = new SerialDTO();
        serialDTO.setId(serialEntity.getId());
        serialDTO.setBatchCode(serialEntity.getBatchCode());
        serialDTO.setSerialCode(serialEntity.getSerialCode());
        serialDTO.setNumberOfSerial(serialEntity.getNumberOfSerial());
        serialDTO.setStatus(serialEntity.getStatus());
        serialDTO.setCreatedBy(serialEntity.getCreatedBy());
        serialDTO.setCreatedAt(serialEntity.getCreatedAt());
        serialDTO.setUpdatedBy(serialEntity.getUpdateBy());
        serialDTO.setUpdatedAt(serialEntity.getUpdateAt());
        return serialDTO;
    }

    public List<SerialDTO> toSerialDTOList(List<SerialEntity> serialEntityList){
        return serialEntityList.stream().map(this::toSerialDTO).collect(Collectors.toList());
    }

    public SerialEntity toSerialEntity(SerialDTO serialDTO){
        SerialEntity serialEntity = new SerialEntity();
        serialEntity.setId(serialDTO.getId());
        serialEntity.setBatchCode(serialDTO.getBatchCode());
        serialEntity.setSerialCode(serialDTO.getSerialCode());
        serialEntity.setNumberOfSerial(serialDTO.getNumberOfSerial());
        serialEntity.setStatus(serialDTO.getStatus());
        return serialEntity;
    }

    public List<SerialEntity> toSerialEntityList(List<SerialDTO> serialDTOList){
        return serialDTOList.stream().map(this::toSerialEntity).collect(Collectors.toList());
    }
}
