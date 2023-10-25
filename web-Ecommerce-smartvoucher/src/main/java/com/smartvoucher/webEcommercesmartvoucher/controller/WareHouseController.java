package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.WareHouseDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IWareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/warehouse")
public class WareHouseController {

    private final IWareHouseService wareHouseService;

    @Autowired
    public WareHouseController(final IWareHouseService wareHouseService) {
        this.wareHouseService = wareHouseService;
    }

    @GetMapping("")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllWareHouse() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get All warehouse success !",
                        this.wareHouseService.getAllWareHouse()
                )
        );
    }

    @PostMapping ("/api/upload")
    public ResponseEntity<ResponseObject> uploadFiles(@RequestParam MultipartFile fileName){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Upload images is completed !",
                        wareHouseService.uploadWarehouseImages(fileName)
                )
        );
    }

    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertWareHouse(@Valid @RequestBody WareHouseDTO wareHouseDTO){
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
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Delete is completed !",
                            "{}"
                    )
            );
    }
}
