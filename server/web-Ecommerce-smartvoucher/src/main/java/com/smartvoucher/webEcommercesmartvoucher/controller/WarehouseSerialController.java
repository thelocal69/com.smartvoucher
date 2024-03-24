
package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.WarehouseSerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.service.IWarehouseSerialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/warehouse_serial")
public class WarehouseSerialController {
    private final IWarehouseSerialService warehouseSerialService;
    @Autowired
    public WarehouseSerialController(final IWarehouseSerialService warehouseSerialService) {
        this.warehouseSerialService = warehouseSerialService;
    }
    @GetMapping("")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllWarehouseSerial(){
        log.info("Get all WarehouseSerial");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get all WarehouseSerial",
                        this.warehouseSerialService.getAllWarehouseSerial()
                )
        );
    }

    @GetMapping("/api/all")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseOutput> getAllWarehouseSerial(
            @RequestParam Long id,
            @RequestParam int page,
            @RequestParam int limit
    ){
        log.info("Get all WarehouseSerial");
        return  new ResponseEntity<>(
                this.warehouseSerialService.getAllWarehouseSerial(
                        id, page, limit),HttpStatus.OK
        );
    }

    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertWarehouseSerial(@Valid @RequestBody WarehouseSerialDTO warehouseSerialDTO){
        log.info("Insert completed");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Insert completed",
                        this.warehouseSerialService.insert(warehouseSerialDTO)
                )
        );
    }
    @DeleteMapping("/api/delete")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> deleteWarehouseSerial(@RequestBody WarehouseSerialDTO warehouseSerialDTO,
                                                                @RequestParam Long idWarehouse, @RequestParam Long idSerial){
        warehouseSerialDTO.getIdWarehouse();
        warehouseSerialDTO.getIdSerial();
        warehouseSerialService.delete(warehouseSerialDTO);
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
