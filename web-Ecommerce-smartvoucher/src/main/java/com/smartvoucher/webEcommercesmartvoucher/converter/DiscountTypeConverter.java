package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.DiscountTypeDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.DiscountTypeEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiscountTypeConverter {
    public DiscountTypeDTO toDiscountTypeDTO(DiscountTypeEntity discountTypeEntity){
        DiscountTypeDTO discountTypeDTO = new DiscountTypeDTO();
        discountTypeDTO.setId(discountTypeEntity.getId());
        discountTypeDTO.setCode(discountTypeEntity.getCode());
        discountTypeDTO.setName(discountTypeEntity.getName());
        discountTypeDTO.setStatus(discountTypeEntity.getStatus());
        discountTypeDTO.setCreatedBy(discountTypeEntity.getCreatedBy());
        discountTypeDTO.setCreatedAt(discountTypeEntity.getCreatedAt());
        discountTypeDTO.setUpdatedBy(discountTypeEntity.getUpdateBy());
        discountTypeDTO.setUpdatedAt(discountTypeEntity.getUpdateAt());
        return discountTypeDTO;
    }

    public List<DiscountTypeDTO> toDiscountTypeDTOList(List<DiscountTypeEntity> discountTypeEntityList){
        return discountTypeEntityList.stream().map(this::toDiscountTypeDTO).collect(Collectors.toList());
    }

    public DiscountTypeEntity toDiscountTypeEntity(DiscountTypeDTO discountTypeDTO){
        DiscountTypeEntity discountTypeEntity = new DiscountTypeEntity();
        discountTypeEntity.setId(discountTypeDTO.getId());
        discountTypeEntity.setCode(discountTypeDTO.getCode());
        discountTypeEntity.setName(discountTypeDTO.getName());
        discountTypeEntity.setStatus(discountTypeDTO.getStatus());
        return discountTypeEntity;
    }

    public List<DiscountTypeEntity> toDiscountTypeEntityList(List<DiscountTypeDTO> discountTypeDTOList){
        return discountTypeDTOList.stream().map(this::toDiscountTypeEntity).collect(Collectors.toList());
    }
}
