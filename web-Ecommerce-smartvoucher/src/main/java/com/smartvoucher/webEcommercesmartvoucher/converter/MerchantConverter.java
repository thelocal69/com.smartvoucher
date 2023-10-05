package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.MerchantEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MerchantConverter {
    public MerchantDTO toMerchantDTO(MerchantEntity merchantEntity){
        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setId(merchantEntity.getId());
        merchantDTO.setMerchantCode(merchantEntity.getMerchantCode());
        merchantDTO.setLegalName(merchantEntity.getLegalName());
        merchantDTO.setLogoUrl(merchantEntity.getLogoUrl());
        merchantDTO.setName(merchantEntity.getName());
        merchantDTO.setEmail(merchantEntity.getEmail());
        merchantDTO.setPhone(merchantEntity.getPhone());
        merchantDTO.setAddress(merchantEntity.getAddress());
        merchantDTO.setDescription(merchantEntity.getDescription());
        merchantDTO.setStatus(merchantEntity.getStatus());
        merchantDTO.setCreatedBy(merchantEntity.getCreatedBy());
        merchantDTO.setCreatedAt(merchantEntity.getCreatedAt());
        merchantDTO.setUpdatedBy(merchantEntity.getUpdateBy());
        merchantDTO.setUpdatedAt(merchantEntity.getUpdateAt());
        return  merchantDTO;
    }

    public List<MerchantDTO> toMerchantDTOList(List<MerchantEntity> merchantEntityList){
        return merchantEntityList.stream().map(this::toMerchantDTO).collect(Collectors.toList());
    }

    public MerchantEntity toMerchantEntity(MerchantDTO merchantDTO){
        MerchantEntity merchantEntity = new MerchantEntity();
        merchantEntity.setId(merchantDTO.getId());
        merchantEntity.setMerchantCode(merchantDTO.getMerchantCode());
        merchantEntity.setLegalName(merchantDTO.getLegalName());
        merchantEntity.setLogoUrl(merchantDTO.getLogoUrl());
        merchantEntity.setName(merchantDTO.getName());
        merchantEntity.setEmail(merchantDTO.getEmail());
        merchantEntity.setPhone(merchantDTO.getPhone());
        merchantEntity.setAddress(merchantDTO.getAddress());
        merchantEntity.setDescription(merchantDTO.getDescription());
        merchantEntity.setStatus(merchantDTO.getStatus());
        return  merchantEntity;
    }

    public MerchantEntity toMerchantEntity(MerchantDTO merchantDTO, MerchantEntity merchantEntity){
        merchantEntity.setId(merchantDTO.getId());
        merchantEntity.setMerchantCode(merchantDTO.getMerchantCode());
        merchantEntity.setLegalName(merchantDTO.getLegalName());
        merchantEntity.setLogoUrl(merchantDTO.getLogoUrl());
        merchantEntity.setName(merchantDTO.getName());
        merchantEntity.setEmail(merchantDTO.getEmail());
        merchantEntity.setPhone(merchantDTO.getPhone());
        merchantEntity.setAddress(merchantDTO.getAddress());
        merchantEntity.setDescription(merchantDTO.getDescription());
        merchantEntity.setStatus(merchantDTO.getStatus());
        return  merchantEntity;
    }

    public List<MerchantEntity> toMerchantEntityList(List<MerchantDTO> merchantDTOList){
        return merchantDTOList.stream().map(this::toMerchantEntity).collect(Collectors.toList());
    }
}
