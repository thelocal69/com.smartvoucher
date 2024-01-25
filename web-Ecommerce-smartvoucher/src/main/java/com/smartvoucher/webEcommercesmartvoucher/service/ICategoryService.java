package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.CategoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> getAllCategory();
    ResponseOutput getAllCategory(int page, int limit, String sortBy, String sortField);
    List<CategoryDTO> getAllCategoryCode(CategoryDTO categoryDTO);
    List<CategoryDTO> searchAllByName(String name);
    List<String> getAllNameByCategory();
    CategoryDTO upsert(CategoryDTO categoryDTO);
    void deleteCategory(CategoryDTO categoryDTO);
    Boolean exitsCategory(CategoryDTO categoryDTO);
    String uploadCategoryImages(MultipartFile fileName);
    String uploadLocalCategoryImages(MultipartFile fileName);
    byte[] readImageUrl(String fileName);
}
