package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.ChainDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.ChainEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChainConverter {
    public ChainDTO toChainDTO(ChainEntity chainEntity){
        return ChainDTO.builder()
                .id(chainEntity.getId())
                .createdBy(chainEntity.getCreatedBy())
                .createdAt(chainEntity.getCreatedAt())
                .updatedBy(chainEntity.getUpdateBy())
                .updatedAt(chainEntity.getUpdateAt())
                .chainCode(chainEntity.getChainCode())
                .name(chainEntity.getName())
                .email(chainEntity.getEmail())
                .legalName(chainEntity.getLegalName())
                .logoUrl(chainEntity.getLogoUrl())
                .phone(chainEntity.getPhone())
                .description(chainEntity.getDescription())
                .address(chainEntity.getAddress())
                .merchantName(chainEntity.getMerchant().getName())
                .status(chainEntity.getStatus())
                .build();
    }

    public List<ChainDTO> toChainDTOList(List<ChainEntity> chainEntityList){
        return chainEntityList.stream().map(this::toChainDTO).collect(Collectors.toList());
    }

    public ChainEntity toChainEntity(ChainDTO chainDTO){
        return ChainEntity.builder()
                .id(chainDTO.getId())
                .chainCode(chainDTO.getChainCode())
                .legalName(chainDTO.getLegalName())
                .name(chainDTO.getName())
                .logoUrl(chainDTO.getLogoUrl())
                .description(chainDTO.getDescription())
                .address(chainDTO.getAddress())
                .phone(chainDTO.getPhone())
                .email(chainDTO.getEmail())
                .status(chainDTO.getStatus())
                .build();
    }

    public ChainEntity toChainEntity(ChainDTO chainDTO, ChainEntity chainEntity){
        chainEntity.setId(chainDTO.getId());
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
