package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.service.IMerchantService;
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
@RequestMapping("/merchant")
public class MerchantController {

    private final IMerchantService merchantService;

    @Autowired
    public MerchantController(final IMerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping("")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllMerchant() {
        log.info("Get All merchant success !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get All merchant success !",
                        this.merchantService.getAllMerchant()
                )
        );
    }

    @GetMapping("/api/search")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> searchMerchantByName(@RequestParam String name) {
        log.info("Search All merchant success !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Search All merchant success !",
                        this.merchantService.searchMerchantByName(name)
                )
        );
    }

    @GetMapping("/api/getName")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllMerchantName() {
        log.info("Search All merchant name success !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Search All merchant name success !",
                        this.merchantService.getALlMerchantName()
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
                        this.merchantService.readImageUrl(fileName)
                );
    }

    @GetMapping("/api/getAll")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseOutput> getAllPageMerchant(
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam String sortBy,
            @RequestParam String sortField
    ) {
        log.info("Get All merchant success !");
        return new ResponseEntity<>(merchantService.getAllMerchant(
                page, limit, sortBy, sortField), HttpStatus.OK);
    }

    @PostMapping ("/api/upload")
    public ResponseEntity<ResponseObject> uploadFiles(@RequestParam MultipartFile fileName){
        log.info("Upload images is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Upload images is completed !",
                        merchantService.uploadMerchantImages(fileName)
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
                        merchantService.uploadLocalMerchantImages(fileName)
                )
        );
    }

    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertMerchant(@Valid @RequestBody MerchantDTO merchantDTO) {
        merchantDTO.setMerchantCode("M-"+
                UUID.randomUUID()
                        .toString()
                        .replace("-","")
                        .substring(0,18)
        );
        log.info("Insert is completed !");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Insert is completed !",
                            this.merchantService.upsertMerchant(merchantDTO)
                    )
            );
    }

    //this is "up-sert", that mean if the instance is not existed or not found, this method is insert the new data
    @PutMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> updateMerchant(@Valid @RequestBody MerchantDTO merchantDTO, @PathVariable Long id) {
        merchantDTO.setId(id);
        log.info("Update is complete !");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Update is complete !",
                            this.merchantService.upsertMerchant(merchantDTO)
                    )
            );
    }

    @DeleteMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> deleteMerchant(@RequestBody MerchantDTO merchantDTO, @PathVariable Long id) {
        merchantDTO.setId(id);
        this.merchantService.deleteMerchant(merchantDTO);
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

