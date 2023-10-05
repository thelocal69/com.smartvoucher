package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.dto.CategoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(final ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllCategory() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "Success",
                        "Get All category success !",
                        this.categoryService.getAllCategory()
                )
        );
    }

    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertCategory(@RequestBody CategoryDTO categoryDTO){
        List<CategoryDTO> category = categoryService.getAllCategoryCode(categoryDTO);
        if (category.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            "Success",
                            "Insert is completed !",
                            this.categoryService.upsert(categoryDTO)
                    )
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            "Failed",
                            "Category code is duplicated !",
                            ""
                    )
            );
        }
    }

    @PutMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id){
        categoryDTO.setId(id);
        boolean exist = categoryService.exitsCategory(categoryDTO);
        if (exist){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            "Success",
                            "Update is completed !",
                            this.categoryService.upsert(categoryDTO)
                    )
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(
                            "Failed",
                            "Cannot update category id = "+id,
                            ""
                    )
            );
        }
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<ResponseObject> deleteCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id){
        categoryDTO.setId(id);
        if (this.categoryService.deleteCategory(categoryDTO)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            "Success",
                            "Delete is completed !",
                            "{}"
                    )
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(
                            "Failed",
                            "Cannot delete category id = "+id,
                            ""
                    )
            );
        }
    }
}
