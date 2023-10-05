package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.CategoryConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.CategoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.CategoryEntity;
import com.smartvoucher.webEcommercesmartvoucher.repository.ICategoryRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    @Autowired
    public CategoryService(final ICategoryRepository categoryRepository, final CategoryConverter categoryConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategory() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        return categoryConverter.toCategoryDTOList(categoryEntityList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategoryCode(CategoryDTO categoryDTO) {
        List<CategoryEntity> category = categoryRepository.findByCategoryCode(categoryDTO.getCategoryCode());
        return categoryConverter.toCategoryDTOList(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDTO upsert(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity;
        if (categoryDTO.getId() != null){
            CategoryEntity oldCategory = categoryRepository.findOneById(categoryDTO.getId());
            categoryEntity = categoryConverter.toCategoryEntity(categoryDTO, oldCategory);
        }else {
            categoryEntity = categoryConverter.toCategoryEntity(categoryDTO);
        }
        return categoryConverter.toCategoryDTO(categoryRepository.save(categoryEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteCategory(CategoryDTO categoryDTO) {
        boolean exists = categoryRepository.existsById(categoryDTO.getId());
        if (exists){
            this.categoryRepository.deleteById(categoryDTO.getId());
            return true;
        }else {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean exitsCategory(CategoryDTO categoryDTO) {
        return categoryRepository.existsById(categoryDTO.getId());
    }
}
