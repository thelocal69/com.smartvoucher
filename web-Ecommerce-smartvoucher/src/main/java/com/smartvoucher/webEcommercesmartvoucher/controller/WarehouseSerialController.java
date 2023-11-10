
package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.WarehouseSerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.keys.WarehouseSerialKeys;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IWarehouseSerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/warehouse_serial")
public class WarehouseSerialController {
    private IWarehouseSerialService warehouseSerialService;
    @Autowired
    public WarehouseSerialController(IWarehouseSerialService warehouseSerialService) {
        this.warehouseSerialService = warehouseSerialService;
    }
    @GetMapping("")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllWarehouseSerial(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get all WarehouseSerial",
                        this.warehouseSerialService.getAllWarehouseSerial()
                )
        );
    }
    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertWarehouseSerial(@Valid @RequestBody WarehouseSerialDTO warehouseSerialDTO){
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
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Delete completed",
                        "{}"
                )
        );
    }


}
