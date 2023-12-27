package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.StoreDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.StoreEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StoreConverter {
    public StoreDTO toStoreDTO(StoreEntity storeEntity){
        return StoreDTO.builder()
                .id(storeEntity.getId())
                .createdBy(storeEntity.getCreatedBy())
                .createdAt(storeEntity.getCreatedAt())
                .updatedBy(storeEntity.getUpdateBy())
                .updatedAt(storeEntity.getUpdateAt())
                .storeCode(storeEntity.getStoreCode())
                .name(storeEntity.getName())
                .address(storeEntity.getAddress())
                .description(storeEntity.getDescription())
                .phone(storeEntity.getPhone())
                .status(storeEntity.getStatus())
                .merchantName(storeEntity.getMerchant().getName())
                .chainName(storeEntity.getChain().getName())
                .build();
    }

    public List<StoreDTO> toStoreDTOList(List<StoreEntity> storeEntityList){
        return storeEntityList.stream().map(this::toStoreDTO).collect(Collectors.toList());
    }

    public StoreEntity toStoreEntity(StoreDTO storeDTO){
        return StoreEntity.builder()
                .id(storeDTO.getId())
                .storeCode(storeDTO.getStoreCode())
                .name(storeDTO.getName())
                .phone(storeDTO.getPhone())
                .address(storeDTO.getAddress())
                .status(storeDTO.getStatus())
                .description(storeDTO.getDescription())
                .build();
    }

    public StoreEntity toStoreEntity(StoreDTO storeDTO, StoreEntity storeEntity){
        storeEntity.setId(storeDTO.getId());
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
