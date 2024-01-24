
package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.WarehouseStoreDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IWarehouseStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/warehouse_store")
public class WarehouseStoreController {
    private final IWarehouseStoreService warehouseStoreService;
    @Autowired
    public WarehouseStoreController(final IWarehouseStoreService warehouseStoreService) {
        this.warehouseStoreService = warehouseStoreService;
    }
    @GetMapping("")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllWarehouseStore(){
        log.info("Get all WarehouseStore");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get all WarehouseStore",
                        this.warehouseStoreService.getAllWarehouseStore()
                )
        );
    }
    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertWarehouseStore(@Valid @RequestBody WarehouseStoreDTO warehouseStoreDTO){
        log.info("Insert completed");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Insert completed",
                        this.warehouseStoreService.insert(warehouseStoreDTO)
                )
        );
    }

    @GetMapping("/api/getId_Store")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> getIdStore(@RequestParam Long idWarehouse){
        log.info("get id store is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "get id store is completed !",
                        this.warehouseStoreService.getWarehouseStoreByIdWarehouse(idWarehouse)
                )
        );
    }

    @DeleteMapping("/api/delete")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> deleteWarehouseStore(@RequestBody WarehouseStoreDTO warehouseStoreDTO,
                                                               @RequestParam Long idWarehouse, @RequestParam Long idStore){
        warehouseStoreDTO.getIdWarehouse();
        warehouseStoreDTO.getIdStore();
        warehouseStoreService.delete(warehouseStoreDTO);
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
