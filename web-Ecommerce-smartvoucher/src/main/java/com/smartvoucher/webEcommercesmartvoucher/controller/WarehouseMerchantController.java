package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.WarehouseMerchantDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IWarehouseMerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/warehouse_merchant")
public class WarehouseMerchantController {
    private final IWarehouseMerchantService warehouseMerchantService;
    @Autowired
    public WarehouseMerchantController(IWarehouseMerchantService warehouseMerchantService) {
        this.warehouseMerchantService = warehouseMerchantService;
    }


    @GetMapping("")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllWarehouseMerchant(){
        log.info("Get All WarehouseMerchant");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get All WarehouseMerchant",
                        this.warehouseMerchantService.getAllWarehouseMerchant()
                )
        );
    }
    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertWarehouseMerchant(@Valid @RequestBody WarehouseMerchantDTO warehouseMerchantDTO){
        log.info("Insert completed");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Insert completed",
                        this.warehouseMerchantService.insert(warehouseMerchantDTO)
                )
        );
    }
    @DeleteMapping("/api/delete")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> deleteWarehouseMerchant(@RequestBody WarehouseMerchantDTO warehouseMerchantDTO,
                                                                  @RequestParam Long idWarehouse, @RequestParam Long idMerchant){
        warehouseMerchantDTO.getIdMerchant();
        warehouseMerchantDTO.getIdWarehouse();
        this.warehouseMerchantService.delete(warehouseMerchantDTO);
        log.info("Delete completed");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Delete completed",
                        "{}"
                )
        );
    }



}
