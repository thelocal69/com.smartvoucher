package com.smartvoucher.webEcommercesmartvoucher.service;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.dto.CategoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> getAllCategory();
    List<CategoryDTO> getAllCategoryCode(CategoryDTO categoryDTO);
    CategoryDTO upsert(CategoryDTO categoryDTO);
    void deleteCategory(CategoryDTO categoryDTO);
    Boolean exitsCategory(CategoryDTO categoryDTO);
    String uploadCategoryImages(MultipartFile fileName);
}
