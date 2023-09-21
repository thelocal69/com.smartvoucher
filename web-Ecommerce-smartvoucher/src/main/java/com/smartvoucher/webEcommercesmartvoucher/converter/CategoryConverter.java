package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.CategoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.CategoryEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryConverter {
    public CategoryDTO toCategoryDTO(CategoryEntity categoryEntity){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryEntity.getId());
        categoryDTO.setCategoryCode(categoryEntity.getCategoryCode());
        categoryDTO.setName(categoryEntity.getName());
        categoryDTO.setStatus(categoryEntity.getStatus());
        categoryDTO.setCreatedBy(categoryEntity.getCreatedBy());
        categoryDTO.setCreatedAt(categoryEntity.getCreatedAt());
        categoryDTO.setUpdatedBy(categoryEntity.getUpdateBy());
        categoryDTO.setUpdatedAt(categoryEntity.getUpdateAt());
        return categoryDTO;
    }

    public List<CategoryDTO> toCategoryDTOList(List<CategoryEntity> categoryEntityList){
        return categoryEntityList.stream().map(this::toCategoryDTO).collect(Collectors.toList());
    }

    public CategoryEntity toCategoryEntity(CategoryDTO categoryDTO){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryDTO.getId());
        categoryEntity.setCategoryCode(categoryDTO.getCategoryCode());
        categoryEntity.setName(categoryDTO.getName());
        categoryEntity.setStatus(categoryDTO.getStatus());
        return categoryEntity;
    }

    public List<CategoryEntity> toCategoryEntityList(List<CategoryDTO> categoryDTOList){
        return categoryDTOList.stream().map(this::toCategoryEntity).collect(Collectors.toList());
    }
}
