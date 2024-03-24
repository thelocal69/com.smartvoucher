package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.WarehouseSerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.WarehouseSerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.keys.WarehouseSerialKeys;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WarehouseSerialConverter {
    public WarehouseSerialKeys toWarehouseSerialKeys(WarehouseSerialDTO warehouseSerialDTO){
        WarehouseSerialKeys keys = new WarehouseSerialKeys();
        keys.setIdSerial(warehouseSerialDTO.getIdSerial());
        keys.setIdWarehouse(warehouseSerialDTO.getIdWarehouse());
        return keys;
    }
    public WarehouseSerialEntity toWarehouseSerialEntity(WarehouseSerialDTO warehouseSerialDTO){
        WarehouseSerialEntity warehouseSerialEntity = new WarehouseSerialEntity();
        warehouseSerialEntity.setKeys(toWarehouseSerialKeys(warehouseSerialDTO));
        warehouseSerialEntity.setCreatedBy(warehouseSerialDTO.getCreatedBy());
        warehouseSerialEntity.setUpdateBy(warehouseSerialDTO.getUpdatedBy());
        warehouseSerialEntity.setCreatedAt(warehouseSerialDTO.getCreatedAt());
        warehouseSerialEntity.setUpdateAt(warehouseSerialDTO.getUpdatedAt());
        return warehouseSerialEntity;
    }
    public WarehouseSerialDTO toWarehouseSerialDTO(WarehouseSerialEntity warehouseSerialEntity){
        WarehouseSerialDTO warehouseSerialDTO = new WarehouseSerialDTO();
        warehouseSerialDTO.setKeys(warehouseSerialEntity.getKeys());
        warehouseSerialDTO.setSerialCode(warehouseSerialEntity.getIdSerial().getSerialCode());
        warehouseSerialDTO.setWarehouseCode(warehouseSerialEntity.getIdWarehouse().getWarehouseCode());
        warehouseSerialDTO.setBannerUrl(warehouseSerialEntity.getIdWarehouse().getBannerUrl());
        warehouseSerialDTO.setWarehouseName(warehouseSerialEntity.getIdWarehouse().getName());
        warehouseSerialDTO.setCategoryName(warehouseSerialEntity.getIdWarehouse().getCategory().getName());
        warehouseSerialDTO.setCreatedAt(warehouseSerialEntity.getCreatedAt());
        warehouseSerialDTO.setUpdatedAt(warehouseSerialEntity.getUpdateAt());
        warehouseSerialDTO.setCreatedBy(warehouseSerialEntity.getCreatedBy());
        warehouseSerialDTO.setUpdatedBy(warehouseSerialEntity.getUpdateBy());
        warehouseSerialDTO.setIdSerial(warehouseSerialEntity.getIdSerial().getId());
        warehouseSerialDTO.setIdWarehouse(warehouseSerialEntity.getIdWarehouse().getId());
        return warehouseSerialDTO;
    }

    public List<WarehouseSerialDTO> toWarehouseSerialDTOList(List<WarehouseSerialEntity> warehouseSerialEntityList){
        List<WarehouseSerialDTO> list = new ArrayList<>();
        for(WarehouseSerialEntity data : warehouseSerialEntityList){
            list.add(toWarehouseSerialDTO(data));
        }
        return list;
    }
    public List<WarehouseSerialEntity> toWarehouseSerialEntityList(List<WarehouseSerialDTO> warehouseSerialDTOList){
        List<WarehouseSerialEntity> list = new ArrayList<>();
        for(WarehouseSerialDTO data : warehouseSerialDTOList){
            list.add(toWarehouseSerialEntity(data));
        }
        return  list;
    }
    public WarehouseSerialEntity toWarehouseSerialEntity(WarehouseSerialEntity entity, WarehouseSerialDTO dto){
        entity.setKeys(dto.getKeys());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdateBy(dto.getUpdatedBy());
        entity.setUpdateAt(dto.getUpdatedAt());
        return entity;
    }
}
