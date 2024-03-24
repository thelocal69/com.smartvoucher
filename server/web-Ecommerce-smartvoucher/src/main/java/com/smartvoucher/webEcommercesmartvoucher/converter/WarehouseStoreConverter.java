
package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.WarehouseStoreDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.WarehouseStoreEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.keys.WarehouseStoreKeys;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WarehouseStoreConverter {
    public WarehouseStoreKeys toWarehouseStoreKeys(WarehouseStoreDTO warehouseStoreDTO){
        WarehouseStoreKeys keys = new WarehouseStoreKeys();
        keys.setIdWarehouse(warehouseStoreDTO.getIdWarehouse());
        keys.setIdStore(warehouseStoreDTO.getIdStore());
        return keys;
    }
    public WarehouseStoreEntity toWarehouseStoreEntity(WarehouseStoreDTO warehouseStoreDTO){
        WarehouseStoreEntity warehouseStoreEntity = new WarehouseStoreEntity();
        warehouseStoreEntity.setKeys(toWarehouseStoreKeys(warehouseStoreDTO));
        warehouseStoreEntity.setCreatedBy(warehouseStoreDTO.getCreatedBy());
        warehouseStoreEntity.setUpdateBy(warehouseStoreDTO.getUpdatedBy());
        warehouseStoreEntity.setCreatedAt(warehouseStoreDTO.getCreatedAt());
        warehouseStoreEntity.setUpdateAt(warehouseStoreDTO.getUpdatedAt());
        return warehouseStoreEntity;
    }
    public WarehouseStoreDTO toWarehouseStoreDTO(WarehouseStoreEntity warehouseStoreEntity){
        WarehouseStoreDTO warehouseStoreDTO = new WarehouseStoreDTO();
        warehouseStoreDTO.setKeys(warehouseStoreEntity.getKeys());
        warehouseStoreDTO.setStoreCode(warehouseStoreEntity.getIdStore().getStoreCode());
        warehouseStoreDTO.setWarehouseCode(warehouseStoreEntity.getIdWarehouse().getWarehouseCode());
        warehouseStoreDTO.setIdWarehouse(warehouseStoreEntity.getIdWarehouse().getId());
        warehouseStoreDTO.setIdStore(warehouseStoreEntity.getIdStore().getId());
        warehouseStoreDTO.setUpdatedAt(warehouseStoreEntity.getUpdateAt());
        warehouseStoreDTO.setCreatedAt(warehouseStoreEntity.getCreatedAt());
        warehouseStoreDTO.setUpdatedBy(warehouseStoreEntity.getUpdateBy());
        warehouseStoreDTO.setCreatedBy(warehouseStoreEntity.getCreatedBy());
        return warehouseStoreDTO;
    }
    public List<WarehouseStoreDTO> toWarehouseStoreDTOList(List<WarehouseStoreEntity> warehouseStoreEntityList){
        List<WarehouseStoreDTO> list = new ArrayList<>();
        for(WarehouseStoreEntity data : warehouseStoreEntityList){
            list.add(toWarehouseStoreDTO(data));
        }
        return list;
    }
    public List<WarehouseStoreEntity> toWarehouseStoreEntityList(List<WarehouseStoreDTO> warehouseStoreDTOList){
        List<WarehouseStoreEntity> list = new ArrayList<>();
        for(WarehouseStoreDTO data : warehouseStoreDTOList){
            list.add(toWarehouseStoreEntity(data));
        }
        return list;
    }
    public WarehouseStoreEntity toWarehouseStoreEntity(WarehouseStoreEntity entity, WarehouseStoreDTO dto){
        entity.setKeys(dto.getKeys());
        entity.setUpdateBy(dto.getUpdatedBy());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdateAt(dto.getUpdatedAt());
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;
    }

}
