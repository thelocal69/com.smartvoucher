package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.converter.CategoryConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.CategoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.CategoryEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.repository.ICategoryRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.ICategoryService;
import com.smartvoucher.webEcommercesmartvoucher.util.UploadGoogleDriveUtil;
import com.smartvoucher.webEcommercesmartvoucher.util.UploadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;
    private final UploadGoogleDriveUtil uploadGoogleDriveUtil;
    private final UploadLocalUtil uploadLocalUtil;
    @Value("${drive_view}")
    private String driveUrl;
    @Value("${domain_file}")
    private String domainFile;

    @Autowired
    public CategoryService(final ICategoryRepository categoryRepository,
                           final CategoryConverter categoryConverter,
                           final UploadGoogleDriveUtil uploadGoogleDriveUtil,
                           final UploadLocalUtil uploadLocalUtil) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
        this.uploadGoogleDriveUtil = uploadGoogleDriveUtil;
        this.uploadLocalUtil = uploadLocalUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategory() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        if (categoryEntityList.isEmpty()){
            log.info("List category is empty !");
            throw new ObjectEmptyException(
                    404, "List category is empty !"
            );
        }
        log.info("Get all category successfully !");
        return categoryConverter.toCategoryDTOList(categoryEntityList);
    }

    @Override
    public ResponseOutput getAllCategory(int page, int limit, String sortBy, String sortField) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.fromString(sortBy), sortField));
        List<CategoryDTO> categoryDTOList = categoryConverter.toCategoryDTOList(
                categoryRepository.findAll(pageable).getContent()
        );
        if (categoryDTOList.isEmpty()){
            log.info("List category is empty !");
            throw new ObjectEmptyException(
                    404, "List category is empty !"
            );
        }
        int totalItem = (int) categoryRepository.count();
        int totalPage = (int) Math.ceil((double) totalItem/limit);
        log.info("Get all category successfully !");
        return new ResponseOutput(
                page,
                totalItem,
                totalPage,
                categoryDTOList
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategoryCode(CategoryDTO categoryDTO) {
        List<CategoryEntity> category = categoryRepository.findByCategoryCode(categoryDTO.getCategoryCode());
        return categoryConverter.toCategoryDTOList(category);
    }

    @Override
    public List<CategoryDTO> searchAllByName(String name) {
        return categoryConverter.toCategoryDTOList(
                categoryRepository.searchAllByNameContainingIgnoreCase(name)
        );
    }

    @Override
    public List<String> getAllNameByCategory() {
        List<String> categoryNameList = categoryRepository.getAllByCategoryName();
        if (categoryNameList.isEmpty()){
            log.info("List category name is empty !");
            throw new ObjectEmptyException(404, "List category name is empty !");
        }
        log.info("Get list category name is successfully !");
        return categoryNameList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDTO upsert(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity;
        if (categoryDTO.getId() != null){
            boolean exist = exitsCategory(categoryDTO);
            if (!exist){
                log.info("Cannot update category id: "+categoryDTO.getId());
                throw new ObjectNotFoundException(
                        404, "Cannot update category id: "+categoryDTO.getId()
                );
            }
            CategoryEntity oldCategory = categoryRepository.findOneById(categoryDTO.getId());
            categoryEntity = categoryConverter.toCategoryEntity(categoryDTO, oldCategory);
            log.info("Update category is completed !");
        }else {
            List<CategoryEntity> categoryEntityList = categoryConverter.toCategoryEntityList(getAllCategoryCode(categoryDTO));
            if (!categoryEntityList.isEmpty()){
                log.info("Category code is duplicated !");
                throw new DuplicationCodeException(
                        400, "Category code is duplicated !"
                );
            }
            categoryEntity = categoryConverter.toCategoryEntity(categoryDTO);
            log.info("Insert category is completed !");
        }
        return categoryConverter.toCategoryDTO(categoryRepository.save(categoryEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(CategoryDTO categoryDTO) {
        boolean exists = categoryRepository.existsById(categoryDTO.getId());
        if (!exists){
            log.info("Cannot delete id: "+categoryDTO.getId());
            throw new ObjectNotFoundException(
                    404, "Cannot delete id: "+categoryDTO.getId()
            );
        }
        this.categoryRepository.deleteById(categoryDTO.getId());
        log.info("Delete category is completed !");
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean exitsCategory(CategoryDTO categoryDTO) {
        return categoryRepository.existsById(categoryDTO.getId());
    }

    @Override
    public String uploadCategoryImages(MultipartFile fileName) {
        String folderId = "17pJEY12p30od5F0aw2BETsguQZK5sTCZ";
        File file = uploadGoogleDriveUtil.uploadImages(fileName, folderId);
        return driveUrl+file.getId();
    }

    @Override
    public String uploadLocalCategoryImages(MultipartFile fileName) {
        String folderName = "category";
        String imageName = uploadLocalUtil.storeFile(fileName, folderName);
        return domainFile+"/category/"+imageName;
    }

    @Override
    public byte[] readImageUrl(String fileName) {
        String folderName = "category";
        return uploadLocalUtil.readFileContent(fileName, folderName);
    }
}
