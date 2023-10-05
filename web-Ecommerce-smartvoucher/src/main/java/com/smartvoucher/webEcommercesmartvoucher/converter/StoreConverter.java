package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.StoreDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.StoreEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StoreConverter {
    public StoreDTO toStoreDTO(StoreEntity storeEntity){
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setId(storeEntity.getId());
        storeDTO.setStoreCode(storeEntity.getStoreCode());
        storeDTO.setName(storeEntity.getName());
        storeDTO.setAddress(storeEntity.getAddress());
        storeDTO.setPhone(storeEntity.getPhone());
        storeDTO.setDescription(storeEntity.getDescription());
        storeDTO.setStatus(storeEntity.getStatus());
        storeDTO.setMerchantCode(storeEntity.getMerchant().getMerchantCode());
        storeDTO.setChainCode(storeEntity.getChain().getChainCode());
        storeDTO.setCreatedBy(storeEntity.getCreatedBy());
        storeDTO.setCreatedAt(storeEntity.getCreatedAt());
        storeDTO.setUpdatedBy(storeEntity.getUpdateBy());
        storeDTO.setUpdatedAt(storeEntity.getUpdateAt());
        return storeDTO;
    }

    public List<StoreDTO> toStoreDTOList(List<StoreEntity> storeEntityList){
        return storeEntityList.stream().map(this::toStoreDTO).collect(Collectors.toList());
    }

    public StoreEntity toStoreEntity(StoreDTO storeDTO){
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setId(storeDTO.getId());
        storeEntity.setStoreCode(storeDTO.getStoreCode());
        storeEntity.setName(storeDTO.getName());
        storeEntity.setAddress(storeDTO.getAddress());
        storeEntity.setPhone(storeDTO.getPhone());
        storeEntity.setDescription(storeDTO.getDescription());
        storeEntity.setStatus(storeDTO.getStatus());
        return storeEntity;
    }

    public StoreEntity toStoreEntity(StoreDTO storeDTO, StoreEntity storeEntity){
        storeEntity.setId(storeDTO.getId());
        storeEntity.setStoreCode(storeDTO.getStoreCode());
        storeEntity.setName(storeDTO.getName());
        storeEntity.setAddress(storeDTO.getAddress());
        storeEntity.setPhone(storeDTO.getPhone());
        storeEntity.setDescription(storeDTO.getDescription());
        storeEntity.setStatus(storeDTO.getStatus());
        return storeEntity;
    }

    public List<StoreEntity> toStoreEntityList(List<StoreDTO> storeDTOList){
        return storeDTOList.stream().map(this::toStoreEntity).collect(Collectors.toList());
    }
}
