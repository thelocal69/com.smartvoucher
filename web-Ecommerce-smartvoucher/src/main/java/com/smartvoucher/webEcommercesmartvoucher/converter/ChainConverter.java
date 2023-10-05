package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.ChainDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.ChainEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChainConverter {
    public ChainDTO toChainDTO(ChainEntity chainEntity){
        ChainDTO chainDTO = new ChainDTO();
        chainDTO.setId(chainEntity.getId());
        chainDTO.setChainCode(chainEntity.getChainCode());
        chainDTO.setName(chainEntity.getName());
        chainDTO.setLegalName(chainEntity.getLegalName());
        chainDTO.setLogoUrl(chainEntity.getLogoUrl());
        chainDTO.setEmail(chainEntity.getEmail());
        chainDTO.setPhone(chainEntity.getPhone());
        chainDTO.setAddress(chainEntity.getAddress());
        chainDTO.setDescription(chainEntity.getDescription());
        chainDTO.setStatus(chainEntity.getStatus());
        chainDTO.setMerchantCode(chainEntity.getMerchant().getMerchantCode());
        chainDTO.setCreatedBy(chainDTO.getCreatedBy());
        chainDTO.setCreatedAt(chainEntity.getCreatedAt());
        chainDTO.setUpdatedBy(chainEntity.getUpdateBy());
        chainDTO.setUpdatedAt(chainEntity.getUpdateAt());
        return chainDTO;
    }

    public List<ChainDTO> toChainDTOList(List<ChainEntity> chainEntityList){
        return chainEntityList.stream().map(this::toChainDTO).collect(Collectors.toList());
    }

    public ChainEntity toChainEntity(ChainDTO chainDTO){
        ChainEntity chainEntity = new ChainEntity();
        chainEntity.setId(chainDTO.getId());
        chainEntity.setChainCode(chainDTO.getChainCode());
        chainEntity.setName(chainDTO.getName());
        chainEntity.setLegalName(chainDTO.getLegalName());
        chainEntity.setLogoUrl(chainDTO.getLogoUrl());
        chainEntity.setEmail(chainDTO.getEmail());
        chainEntity.setPhone(chainDTO.getPhone());
        chainEntity.setAddress(chainDTO.getAddress());
        chainEntity.setDescription(chainDTO.getDescription());
        chainEntity.setStatus(chainDTO.getStatus());
        return chainEntity;
    }

    public ChainEntity toChainEntity(ChainDTO chainDTO, ChainEntity chainEntity){
        chainEntity.setId(chainDTO.getId());
        chainEntity.setChainCode(chainDTO.getChainCode());
        chainEntity.setName(chainDTO.getName());
        chainEntity.setLegalName(chainDTO.getLegalName());
        chainEntity.setLogoUrl(chainDTO.getLogoUrl());
        chainEntity.setEmail(chainDTO.getEmail());
        chainEntity.setPhone(chainDTO.getPhone());
        chainEntity.setAddress(chainDTO.getAddress());
        chainEntity.setDescription(chainDTO.getDescription());
        chainEntity.setStatus(chainDTO.getStatus());
        return chainEntity;
    }

    public List<ChainEntity> toChainEntityList(List<ChainDTO> chainDTOList){
        return chainDTOList.stream().map(this::toChainEntity).collect(Collectors.toList());
    }
}
