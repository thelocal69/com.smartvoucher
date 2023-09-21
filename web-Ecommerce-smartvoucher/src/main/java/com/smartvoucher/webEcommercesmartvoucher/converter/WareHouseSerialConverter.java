package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.WareHouseSerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseSerialEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WareHouseSerialConverter {
    public WareHouseSerialDTO toWareHouseSerialDTO(WareHouseSerialEntity wareHouseSerialEntity){
        WareHouseSerialDTO wareHouseSerialDTO = new WareHouseSerialDTO();
        wareHouseSerialDTO.setIdWareHouse(wareHouseSerialEntity.getWareHouse().getId());
        wareHouseSerialDTO.setIdSerial(wareHouseSerialEntity.getSerial().getId());
        wareHouseSerialDTO.setWareHouseCode(wareHouseSerialEntity.getWareHouse().getWarehouseCode());
        wareHouseSerialDTO.setSerialCode(wareHouseSerialEntity.getSerial().getSerialCode());
        wareHouseSerialDTO.setCreatedBy(wareHouseSerialEntity.getCreatedBy());
        wareHouseSerialDTO.setCreatedAt(wareHouseSerialEntity.getCreatedAt());
        wareHouseSerialDTO.setUpdatedBy(wareHouseSerialEntity.getUpdatedBy());
        wareHouseSerialDTO.setUpdatedAt(wareHouseSerialEntity.getUpdatedAt());
        return wareHouseSerialDTO;
    }

    public List<WareHouseSerialDTO> toWareHouseSerialDTOList(List<WareHouseSerialEntity> wareHouseSerialEntityList){
        return wareHouseSerialEntityList.stream().map(this::toWareHouseSerialDTO).collect(Collectors.toList());
    }
}
