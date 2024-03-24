package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.DiscountTypeDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.DiscountTypeEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiscountTypeConverter {
    public DiscountTypeDTO toDiscountTypeDTO(DiscountTypeEntity discountTypeEntity){
        return DiscountTypeDTO.builder()
                .id(discountTypeEntity.getId())
                .createdBy(discountTypeEntity.getCreatedBy())
                .createdAt(discountTypeEntity.getCreatedAt())
                .updatedBy(discountTypeEntity.getUpdateBy())
                .updatedAt(discountTypeEntity.getUpdateAt())
                .code(discountTypeEntity.getCode())
                .name(discountTypeEntity.getName())
                .status(discountTypeEntity.getStatus())
                .build();
    }

    public List<DiscountTypeDTO> toDiscountTypeDTOList(List<DiscountTypeEntity> discountTypeEntityList){
        return discountTypeEntityList.stream().map(this::toDiscountTypeDTO).collect(Collectors.toList());
    }

    public DiscountTypeEntity toDiscountTypeEntity(DiscountTypeDTO discountTypeDTO){
        return DiscountTypeEntity.builder()
                .id(discountTypeDTO.getId())
                .code(discountTypeDTO.getCode())
                .name(discountTypeDTO.getName())
                .status(discountTypeDTO.getStatus())
                .build();
    }

    public DiscountTypeEntity toDiscountTypeEntity(DiscountTypeDTO discountTypeDTO, DiscountTypeEntity discountTypeEntity){
        discountTypeEntity.setId(discountTypeDTO.getId());
        discountTypeEntity.setName(discountTypeDTO.getName());
        discountTypeEntity.setStatus(discountTypeDTO.getStatus());
        return discountTypeEntity;
    }

    public List<DiscountTypeEntity> toDiscountTypeEntityList(List<DiscountTypeDTO> discountTypeDTOList){
        return discountTypeDTOList.stream().map(this::toDiscountTypeEntity).collect(Collectors.toList());
    }
}
