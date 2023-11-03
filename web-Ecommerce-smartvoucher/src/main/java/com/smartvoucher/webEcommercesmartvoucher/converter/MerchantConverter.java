package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.MerchantEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MerchantConverter {
    public MerchantDTO toMerchantDTO(MerchantEntity merchantEntity){
        return  MerchantDTO.builder()
                .id(merchantEntity.getId())
                .createdBy(merchantEntity.getCreatedBy())
                .createdAt(merchantEntity.getCreatedAt())
                .updatedBy(merchantEntity.getUpdateBy())
                .updatedAt(merchantEntity.getUpdateAt())
                .merchantCode(merchantEntity.getMerchantCode())
                .name(merchantEntity.getName())
                .legalName(merchantEntity.getLegalName())
                .description(merchantEntity.getDescription())
                .email(merchantEntity.getEmail())
                .logoUrl(merchantEntity.getLogoUrl())
                .phone(merchantEntity.getPhone())
                .address(merchantEntity.getAddress())
                .status(merchantEntity.getStatus())
                .build();
    }

    public List<MerchantDTO> toMerchantDTOList(List<MerchantEntity> merchantEntityList){
        return merchantEntityList.stream().map(this::toMerchantDTO).collect(Collectors.toList());
    }

    public MerchantEntity toMerchantEntity(MerchantDTO merchantDTO){
        return  MerchantEntity.builder()
                .id(merchantDTO.getId())
                .merchantCode(merchantDTO.getMerchantCode())
                .name(merchantDTO.getName())
                .legalName(merchantDTO.getLegalName())
                .email(merchantDTO.getEmail())
                .phone(merchantDTO.getPhone())
                .logoUrl(merchantDTO.getLogoUrl())
                .address(merchantDTO.getAddress())
                .description(merchantDTO.getDescription())
                .status(merchantDTO.getStatus())
                .build();
    }

    public MerchantEntity toMerchantEntity(MerchantDTO merchantDTO, MerchantEntity merchantEntity){
        merchantEntity.setId(merchantDTO.getId());
        merchantEntity.setName(merchantDTO.getName());
        merchantEntity.setLegalName(merchantDTO.getLegalName());
        merchantEntity.setEmail(merchantDTO.getEmail());
        merchantEntity.setLogoUrl(merchantDTO.getLogoUrl());
        merchantEntity.setAddress(merchantDTO.getAddress());
        merchantEntity.setDescription(merchantDTO.getDescription());
        merchantEntity.setPhone(merchantDTO.getPhone());
        merchantEntity.setStatus(merchantDTO.getStatus());
        return merchantEntity;
    }

    public List<MerchantEntity> toMerchantEntityList(List<MerchantDTO> merchantDTOList){
        return merchantDTOList.stream().map(this::toMerchantEntity).collect(Collectors.toList());
    }
}
