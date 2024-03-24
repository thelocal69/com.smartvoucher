
package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.WarehouseMerchantDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.WarehouseMerchantEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.keys.WarehouseMerchantKeys;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WarehouseMerchantConverter {

    public WarehouseMerchantKeys toWarehouseMerchantKeys(WarehouseMerchantDTO warehouseMerchantDTO){

        WarehouseMerchantKeys warehouseMerchantKeys = new WarehouseMerchantKeys();
        warehouseMerchantKeys.setIdWarehouse(warehouseMerchantDTO.getIdWarehouse());
        warehouseMerchantKeys.setIdMerchant(warehouseMerchantDTO.getIdMerchant());
        return warehouseMerchantKeys;
    }
    public WarehouseMerchantEntity toWarehouseMerchantEntity(WarehouseMerchantDTO warehouseMerchantDTO){

        WarehouseMerchantEntity warehouseMerchantEntity = new WarehouseMerchantEntity();
        warehouseMerchantEntity.setKeys(toWarehouseMerchantKeys(warehouseMerchantDTO));
        warehouseMerchantEntity.setCreatedBy(warehouseMerchantDTO.getCreatedBy());
        warehouseMerchantEntity.setUpdatedBy(warehouseMerchantDTO.getUpdatedBy());
        warehouseMerchantEntity.setCreatedAt(warehouseMerchantDTO.getCreatedAt());
        warehouseMerchantEntity.setUpdatedAt(warehouseMerchantDTO.getUpdatedAt());
        return warehouseMerchantEntity;
    }
    public WarehouseMerchantDTO toWarehouseMerchantDTO(WarehouseMerchantEntity warehouseMerchantEntity){
        WarehouseMerchantDTO warehouseMerchantDTO = new WarehouseMerchantDTO();
        warehouseMerchantDTO.setKeys(warehouseMerchantEntity.getKeys());
        warehouseMerchantDTO.setMerchantCode(warehouseMerchantEntity.getIdMerchant().getMerchantCode());
        warehouseMerchantDTO.setWarehouseCode(warehouseMerchantEntity.getIdWarehouse().getWarehouseCode());
        warehouseMerchantDTO.setRoleCode(warehouseMerchantEntity.getIdRole().getRoleCode());
        warehouseMerchantDTO.setCreatedBy(warehouseMerchantEntity.getCreatedBy());
        warehouseMerchantDTO.setUpdatedBy(warehouseMerchantEntity.getUpdatedBy());
        warehouseMerchantDTO.setCreatedAt(warehouseMerchantEntity.getCreatedAt());
        warehouseMerchantDTO.setUpdatedAt(warehouseMerchantEntity.getUpdatedAt());
        warehouseMerchantDTO.setIdWarehouse(warehouseMerchantEntity.getIdWarehouse().getId());
        warehouseMerchantDTO.setIdMerchant(warehouseMerchantEntity.getIdMerchant().getId());
        return warehouseMerchantDTO;
    }
    public List<WarehouseMerchantDTO> toWarehouseMerchantDTOList(List<WarehouseMerchantEntity> warehouseMerchantEntityList){
        List<WarehouseMerchantDTO> list = new ArrayList<>();
        for (WarehouseMerchantEntity data : warehouseMerchantEntityList){
            list.add(toWarehouseMerchantDTO(data));
        }
        return list;
    }
    public List<WarehouseMerchantEntity> toWarehouseMerchantEntityList(List<WarehouseMerchantDTO> warehouseMerchantDTOList){
        List<WarehouseMerchantEntity> list = new ArrayList<>();
        for(WarehouseMerchantDTO data : warehouseMerchantDTOList){
            list.add(toWarehouseMerchantEntity(data));
        }
        return list;
    }
    public WarehouseMerchantEntity toWarehousMerchantEntity(WarehouseMerchantEntity entity, WarehouseMerchantDTO dto){
        entity.setKeys(toWarehouseMerchantKeys(dto));
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedBy(dto.getUpdatedBy());
        return entity;
    }

}
