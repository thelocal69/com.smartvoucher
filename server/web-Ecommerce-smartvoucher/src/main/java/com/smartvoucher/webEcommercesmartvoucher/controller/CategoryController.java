package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.CategoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(final ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/all")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllCategory() {
        log.info("Get All category success !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get All category success !",
                        this.categoryService.getAllCategory()
                )
        );
    }

    @GetMapping("/api/getName")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllCategoryName() {
        log.info("Get All category name success !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get All category name success !",
                        this.categoryService.getAllNameByCategory()
                )
        );
    }

    @GetMapping("/{fileName}")
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> getImageCategory(
            @PathVariable String fileName
    ) {
        log.info("Get image category success !");
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(
                        this.categoryService.readImageUrl(fileName)
                );
    }

    @GetMapping("/api/search")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> searchAllByName(@RequestParam String name) {
        log.info("Search All category success !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Search All category success !",
                        this.categoryService.searchAllByName(name)
                )
        );
    }

    @GetMapping("/api/getAll")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseOutput> getAllCategory(
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam String sortBy,
            @RequestParam String sortField) {
        log.info("Get All category success !");
        return new ResponseEntity<>(categoryService.getAllCategory(
                page, limit, sortBy, sortField), HttpStatus.OK);
    }

    @PostMapping ("/api/upload")
    public ResponseEntity<ResponseObject> uploadFiles(@RequestParam MultipartFile fileName){
        log.info("Upload images is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Upload images is completed !",
                        categoryService.uploadCategoryImages(fileName)
                )
        );
    }

    @PostMapping ("/api/local_upload")
    public ResponseEntity<ResponseObject> uploadLocalFiles(@RequestParam MultipartFile fileName){
        log.info("Upload images is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Upload images is completed !",
                        categoryService.uploadLocalCategoryImages(fileName)
                )
        );
    }

    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        categoryDTO.setCategoryCode("CA-"+
                UUID.randomUUID()
                        .toString()
                        .replace("-","")
                        .substring(0, 17)
        );
        log.info("Insert is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Insert is completed !",
                            this.categoryService.upsert(categoryDTO)
                    )
            );
    }

    @PutMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long id){
        categoryDTO.setId(id);
        log.info("Update is completed !");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Update is completed !",
                            this.categoryService.upsert(categoryDTO)
                    )
            );
        }

    @DeleteMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> deleteCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id) {
        categoryDTO.setId(id);
        this.categoryService.deleteCategory(categoryDTO);
        log.info("Delete is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Delete is completed !",
                        "{}"
                )
        );
    }
}
