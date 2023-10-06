package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.WareHouseDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WareHouseConverter {
    public WareHouseDTO toWareHouseDTO(WareHouseEntity wareHouseEntity){
        WareHouseDTO wareHouseDTO = new WareHouseDTO();
        wareHouseDTO.setId(wareHouseEntity.getId());
        wareHouseDTO.setWarehouseCode(wareHouseEntity.getWarehouseCode());
        wareHouseDTO.setThumbnailUrl(wareHouseEntity.getThumbnailUrl());
        wareHouseDTO.setBannerUrl(wareHouseEntity.getBannerUrl());
        wareHouseDTO.setDiscountAmount(wareHouseEntity.getDiscountAmount());
        wareHouseDTO.setMaxDiscountAmount(wareHouseEntity.getMaxDiscountAmount());
        wareHouseDTO.setName(wareHouseEntity.getName());
        wareHouseDTO.setTermOfUse(wareHouseEntity.getTermOfUse());
        wareHouseDTO.setDescription(wareHouseEntity.getDescription());
        wareHouseDTO.setStatus(wareHouseEntity.getStatus());
        wareHouseDTO.setAvailableFrom(wareHouseEntity.getAvailableFrom());
        wareHouseDTO.setAvailableTo(wareHouseEntity.getAvailableTo());
        wareHouseDTO.setShowOnWeb(wareHouseEntity.getShowOnWeb());
        wareHouseDTO.setVoucherChannel(wareHouseEntity.getVoucherChannel());
        wareHouseDTO.setCapacity(wareHouseEntity.getCapacity());
        wareHouseDTO.setDiscountTypeCode(wareHouseEntity.getDiscountType().getCode());
        wareHouseDTO.setCategoryCode(wareHouseEntity.getCategory().getCategoryCode());
        wareHouseDTO.setCreatedBy(wareHouseEntity.getCreatedBy());
        wareHouseDTO.setCreatedAt(wareHouseEntity.getCreatedAt());
        wareHouseDTO.setUpdatedBy(wareHouseEntity.getUpdateBy());
        wareHouseDTO.setUpdatedAt(wareHouseEntity.getUpdateAt());
        return wareHouseDTO;
    }

    public List<WareHouseDTO> toWareHouseDTOList(List<WareHouseEntity> wareHouseEntityList){
        return wareHouseEntityList.stream().map(this::toWareHouseDTO).collect(Collectors.toList());
    }

    public WareHouseEntity toWareHouseEntity(WareHouseDTO wareHouseDTO){
        WareHouseEntity wareHouseEntity = new WareHouseEntity();
        wareHouseEntity.setId(wareHouseDTO.getId());
        wareHouseEntity.setWarehouseCode(wareHouseDTO.getWarehouseCode());
        wareHouseEntity.setThumbnailUrl(wareHouseDTO.getThumbnailUrl());
        wareHouseEntity.setBannerUrl(wareHouseDTO.getBannerUrl());
        wareHouseEntity.setDiscountAmount(wareHouseDTO.getDiscountAmount());
        wareHouseEntity.setMaxDiscountAmount(wareHouseDTO.getMaxDiscountAmount());
        wareHouseEntity.setName(wareHouseDTO.getName());
        wareHouseEntity.setTermOfUse(wareHouseDTO.getTermOfUse());
        wareHouseEntity.setDescription(wareHouseDTO.getDescription());
        wareHouseEntity.setStatus(wareHouseDTO.getStatus());
        wareHouseEntity.setAvailableFrom(wareHouseDTO.getAvailableFrom());
        wareHouseEntity.setAvailableTo(wareHouseDTO.getAvailableTo());
        wareHouseEntity.setShowOnWeb(wareHouseDTO.getShowOnWeb());
        wareHouseEntity.setVoucherChannel(wareHouseDTO.getVoucherChannel());
        wareHouseEntity.setCapacity(wareHouseDTO.getCapacity());
        return wareHouseEntity;
    }

    public WareHouseEntity toWareHouseEntity(WareHouseDTO wareHouseDTO, WareHouseEntity wareHouseEntity){
        wareHouseEntity.setId(wareHouseDTO.getId());
        wareHouseEntity.setWarehouseCode(wareHouseDTO.getWarehouseCode());
        wareHouseEntity.setThumbnailUrl(wareHouseDTO.getThumbnailUrl());
        wareHouseEntity.setBannerUrl(wareHouseDTO.getBannerUrl());
        wareHouseEntity.setDiscountAmount(wareHouseDTO.getDiscountAmount());
        wareHouseEntity.setMaxDiscountAmount(wareHouseDTO.getMaxDiscountAmount());
        wareHouseEntity.setName(wareHouseDTO.getName());
        wareHouseEntity.setTermOfUse(wareHouseDTO.getTermOfUse());
        wareHouseEntity.setDescription(wareHouseDTO.getDescription());
        wareHouseEntity.setStatus(wareHouseDTO.getStatus());
        wareHouseEntity.setAvailableFrom(wareHouseDTO.getAvailableFrom());
        wareHouseEntity.setAvailableTo(wareHouseDTO.getAvailableTo());
        wareHouseEntity.setShowOnWeb(wareHouseDTO.getShowOnWeb());
        wareHouseEntity.setVoucherChannel(wareHouseDTO.getVoucherChannel());
        wareHouseEntity.setCapacity(wareHouseDTO.getCapacity());
        return wareHouseEntity;
    }

    public List<WareHouseEntity> toWareHouseEntityList(List<WareHouseDTO> wareHouseDTOList){
        return wareHouseDTOList.stream().map(this::toWareHouseEntity).collect(Collectors.toList());
    }
}
