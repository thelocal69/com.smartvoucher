package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.WareHouseMerchantDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseMerchantEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WareHouseMerchantConverter {
    public WareHouseMerchantDTO toWareHouseMerchantDTO(WareHouseMerchantEntity wareHouseMerchantEntity){
        WareHouseMerchantDTO wareHouseMerchantDTO = new WareHouseMerchantDTO();
        wareHouseMerchantDTO.setIdWareHouse(wareHouseMerchantEntity.getWareHouse().getId());
        wareHouseMerchantDTO.setIdMerchant(wareHouseMerchantEntity.getMerchant().getId());
        wareHouseMerchantDTO.setWareHouseCode(wareHouseMerchantEntity.getWareHouse().getWarehouseCode());
        wareHouseMerchantDTO.setMerchantCode(wareHouseMerchantEntity.getMerchant().getMerchantCode());
        wareHouseMerchantDTO.setCreatedBy(wareHouseMerchantEntity.getCreatedBy());
        wareHouseMerchantDTO.setCreatedAt(wareHouseMerchantEntity.getCreatedAt());
        wareHouseMerchantDTO.setUpdatedBy(wareHouseMerchantEntity.getUpdatedBy());
        wareHouseMerchantDTO.setUpdatedAt(wareHouseMerchantEntity.getUpdatedAt());
        return wareHouseMerchantDTO;
    }

    public List<WareHouseMerchantDTO> toWareHouseMerchantDTOList(List<WareHouseMerchantEntity> wareHouseMerchantEntityList){
        return wareHouseMerchantEntityList.stream().map(this::toWareHouseMerchantDTO).collect(Collectors.toList());
    }

}
