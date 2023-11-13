package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.converter.CategoryConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.CategoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.CategoryEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.repository.ICategoryRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.ICategoryService;
import com.smartvoucher.webEcommercesmartvoucher.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;
    private final UploadUtil uploadUtil;

    @Autowired
    public CategoryService(final ICategoryRepository categoryRepository,
                           final CategoryConverter categoryConverter,
                           final UploadUtil uploadUtil) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
        this.uploadUtil = uploadUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategory() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        if (categoryEntityList.isEmpty()){
            throw new ObjectEmptyException(
                    404, "List category is empty !"
            );
        }
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
            boolean exist = exitsCategory(categoryDTO);
            if (!exist){
                throw new ObjectNotFoundException(
                        404, "Cannot update category id: "+categoryDTO.getId()
                );
            }
            CategoryEntity oldCategory = categoryRepository.findOneById(categoryDTO.getId());
            categoryEntity = categoryConverter.toCategoryEntity(categoryDTO, oldCategory);
        }else {
            List<CategoryEntity> categoryEntityList = categoryConverter.toCategoryEntityList(getAllCategoryCode(categoryDTO));
            if (!categoryEntityList.isEmpty()){
                throw new DuplicationCodeException(
                        400, "Category code is duplicated !"
                );
            }
            categoryEntity = categoryConverter.toCategoryEntity(categoryDTO);
        }
        return categoryConverter.toCategoryDTO(categoryRepository.save(categoryEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(CategoryDTO categoryDTO) {
        boolean exists = categoryRepository.existsById(categoryDTO.getId());
        if (!exists){
            throw new ObjectNotFoundException(
                    404, "Cannot delete id: "+categoryDTO.getId()
            );
        }
        this.categoryRepository.deleteById(categoryDTO.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean exitsCategory(CategoryDTO categoryDTO) {
        return categoryRepository.existsById(categoryDTO.getId());
    }

    @Override
    public File uploadCategoryImages(MultipartFile fileName) {
        String folderId = "17pJEY12p30od5F0aw2BETsguQZK5sTCZ";
        return uploadUtil.uploadImages(fileName, folderId);
    }
}
