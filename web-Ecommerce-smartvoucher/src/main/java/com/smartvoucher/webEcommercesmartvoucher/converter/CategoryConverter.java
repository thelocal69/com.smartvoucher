package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.CategoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.CategoryEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryConverter {
    public CategoryDTO toCategoryDTO(CategoryEntity categoryEntity){
        return CategoryDTO.builder()
                .id(categoryEntity.getId())
                .createdBy(categoryEntity.getCreatedBy())
                .createdAt(categoryEntity.getCreatedAt())
                .updatedBy(categoryEntity.getUpdateBy())
                .updatedAt(categoryEntity.getUpdateAt())
                .categoryCode(categoryEntity.getCategoryCode())
                .name(categoryEntity.getName())
                .bannerUrl(categoryEntity.getBannerUrl())
                .status(categoryEntity.getStatus())
                .build();
    }

    public List<CategoryDTO> toCategoryDTOList(List<CategoryEntity> categoryEntityList){
        return categoryEntityList.stream().map(this::toCategoryDTO).collect(Collectors.toList());
    }

    public CategoryEntity toCategoryEntity(CategoryDTO categoryDTO){
        return CategoryEntity.builder()
                .id(categoryDTO.getId())
                .categoryCode(categoryDTO.getCategoryCode())
                .name(categoryDTO.getName())
                .bannerUrl(categoryDTO.getBannerUrl())
                .status(categoryDTO.getStatus())
                .build();
    }

    public CategoryEntity toCategoryEntity(CategoryDTO categoryDTO, CategoryEntity categoryEntity){
        categoryEntity.setId(categoryDTO.getId());
        categoryEntity.setName(categoryDTO.getName());
        categoryEntity.setStatus(categoryDTO.getStatus());
        categoryEntity.setBannerUrl(categoryDTO.getBannerUrl());
        return categoryEntity;
    }

    public List<CategoryEntity> toCategoryEntityList(List<CategoryDTO> categoryDTOList){
        return categoryDTOList.stream().map(this::toCategoryEntity).collect(Collectors.toList());
    }
}
