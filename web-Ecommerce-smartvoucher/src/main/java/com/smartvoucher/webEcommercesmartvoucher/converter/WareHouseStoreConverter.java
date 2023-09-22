package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.WareHouseStoreDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseStoreEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WareHouseStoreConverter {
    public WareHouseStoreDTO toWareHouseStoreDTO(WareHouseStoreEntity wareHouseStoreEntity){
        WareHouseStoreDTO wareHouseStoreDTO = new WareHouseStoreDTO();
        wareHouseStoreDTO.setIdWareHouse(wareHouseStoreEntity.getWareHouse().getId());
        wareHouseStoreDTO.setIdStore(wareHouseStoreEntity.getStore().getId());
        wareHouseStoreDTO.setWareHouseCode(wareHouseStoreEntity.getWareHouse().getWarehouseCode());
        wareHouseStoreDTO.setStoreCode(wareHouseStoreEntity.getStore().getStoreCode());
        wareHouseStoreDTO.setCreatedBy(wareHouseStoreEntity.getCreatedBy());
        wareHouseStoreDTO.setCreatedAt(wareHouseStoreEntity.getCreatedAt());
        wareHouseStoreDTO.setUpdatedBy(wareHouseStoreEntity.getUpdatedBy());
        wareHouseStoreDTO.setUpdatedAt(wareHouseStoreEntity.getUpdatedAt());
        return wareHouseStoreDTO;
    }

    public List<WareHouseStoreDTO> toWareHouseStoreDTOList(List<WareHouseStoreEntity> wareHouseStoreEntityList){
        return wareHouseStoreEntityList.stream().map(this::toWareHouseStoreDTO).collect(Collectors.toList());
    }
}
