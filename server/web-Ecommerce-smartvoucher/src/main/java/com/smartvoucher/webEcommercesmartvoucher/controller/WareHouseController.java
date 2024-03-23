package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.WareHouseDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.service.IWareHouseService;
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
@RequestMapping("/warehouse")
public class WareHouseController {

    private final IWareHouseService wareHouseService;

    @Autowired
    public WareHouseController(final IWareHouseService wareHouseService) {
        this.wareHouseService = wareHouseService;
    }

    @GetMapping("/api/all")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllWareHouse() {
        log.info("Get All warehouse success !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get All warehouse success !",
                        this.wareHouseService.getAllWareHouse()
                )
        );
    }

    @GetMapping("/{fileName}")
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> getLocalImage(
            @PathVariable String fileName
    ) {
        log.info("Get image is completed !");
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(
                        this.wareHouseService.readImageUrl(fileName)
                );
    }

    @GetMapping("/api/getAll")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseOutput> getAllWareHouse(
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam String sortBy,
            @RequestParam String sortField
    ) {
        log.info("Get All warehouse success !");
        return new ResponseEntity<>(wareHouseService.getAllWareHouse(page, limit, sortBy, sortField), HttpStatus.OK);
    }

    @GetMapping("/api/get/label")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseOutput> getAllWareHouseByLabel(
            @RequestParam String slug,
            @RequestParam int page,
            @RequestParam int limit
    ) {
        log.info("Get All warehouse success !");
        return new ResponseEntity<>(wareHouseService.getAllWarehousesByLabel(slug, page, limit), HttpStatus.OK);
    }

    @GetMapping("/api/search")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> searchWarehouseByName(@RequestParam String name) {
        log.info("Get All warehouse success !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get All warehouse success !",
                        this.wareHouseService.searchByWarehouseName(name)
                )
        );
    }

    @GetMapping("/api/search_name")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> searchWarehouseNameByName(@RequestParam String name) {
        log.info("Get All warehouse name !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get All warehouse name !",
                        this.wareHouseService.searchAllWarehouseName(name)
                )
        );
    }

    @GetMapping("/api/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getWarehouseById(@PathVariable Long id){
        log.info("Get warehouse detail success");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get warehouse detail success",
                        this.wareHouseService.getWarehouseById(id)
                )
        );
    }

    @PostMapping ("/api/upload/banner")
    public ResponseEntity<ResponseObject> uploadBanner(@RequestParam MultipartFile fileName){
        log.info("Upload banner is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Upload banner is completed !",
                        wareHouseService.uploadWarehouseImages(fileName)
                )
        );
    }

    @PostMapping ("/api/upload/thumbnail")
    public ResponseEntity<ResponseObject> uploadThumbnail(@RequestParam MultipartFile fileName){
        log.info("Upload thumbnail is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Upload thumbnail is completed !",
                        wareHouseService.uploadWarehouseImages(fileName)
                )
        );
    }

    @PostMapping ("/api/local_banner")
    public ResponseEntity<ResponseObject> uploadLocalBanner(@RequestParam MultipartFile fileName){
        log.info("Upload images is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Upload images is completed !",
                        wareHouseService.uploadLocalWarehouseImages(fileName)
                )
        );
    }

    @PostMapping ("/api/local_thumbnail")
    public ResponseEntity<ResponseObject> uploadLocalThumbnail(@RequestParam MultipartFile fileName){
        log.info("Upload images is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Upload images is completed !",
                        wareHouseService.uploadLocalWarehouseImages(fileName)
                )
        );
    }

    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertWareHouse(@Valid @RequestBody WareHouseDTO wareHouseDTO){
        wareHouseDTO.setWarehouseCode("W-"+
                UUID.randomUUID()
                        .toString()
                        .replace("-","")
                        .substring(0,18)
        );
        wareHouseDTO.setPrice(
                (wareHouseDTO.getOriginalPrice()
                        *
                        wareHouseDTO.getMaxDiscountAmount()) / 100
        );
        log.info("Insert is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Insert is completed !",
                            this.wareHouseService.upsert(wareHouseDTO)
                    )
            );
    }

    @PutMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> updateWareHouse(@Valid @RequestBody WareHouseDTO wareHouseDTO, @PathVariable Long id){
        wareHouseDTO.setId(id);
        log.info("Update is completed !");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200,
                            "Update is completed !",
                            this.wareHouseService.upsert(wareHouseDTO)
                    )
            );
    }

    @DeleteMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> deleteWareHouse(@RequestBody WareHouseDTO wareHouseDTO, @PathVariable Long id){
        wareHouseDTO.setId(id);
       this.wareHouseService.deleteWareHouse(wareHouseDTO);
        log.info("Delete is completed !");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Delete is completed !",
                            "{}"
                    )
            );
    }
    @GetMapping("/by-label-id/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getWarehousesByLabel(@PathVariable int id) {
        log.info("Get All warehouse by label successfully !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get All warehouse by label successfully !",
                        this.wareHouseService.getAllWarehousesByLabel(id)
                )
        );
    }

    @GetMapping("/api/category_name")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseOutput> getWarehouseByCategoryName(
            @RequestParam String name,
            @RequestParam int page,
            @RequestParam int limit
    ){
        log.info("Get all Warehouse by category name is successfully !");
        return new ResponseEntity<>(wareHouseService.getAllWarehouseByCategoryName(
                name, page, limit), HttpStatus.OK);
    }
}
