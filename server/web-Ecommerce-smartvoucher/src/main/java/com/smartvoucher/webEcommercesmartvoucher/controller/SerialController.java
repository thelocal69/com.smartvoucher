package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.service.ISerialService;
import com.smartvoucher.webEcommercesmartvoucher.service.impl.SerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/serial")
public class SerialController {

    private final ISerialService serialService;

    @Autowired
    public SerialController(SerialService serialService) {
        this.serialService = serialService;
    }

    @GetMapping("/api/list-serial")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllSerial(){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.serialService.getAllSerial());
    }

    @GetMapping("/api/getAll")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseOutput> getAllSerial(
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam String sortBy,
            @RequestParam String sortField
    ){
        return new ResponseEntity<>(serialService.getAllSerial(page, limit, sortBy, sortField), HttpStatus.OK);
    }

    @PostMapping("/api/add-serial")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertSerial(@RequestParam long idWarehouse,
                                                       @RequestParam String batchCode,
                                                       @RequestParam int numberOfSerial){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.serialService.insertSerial(idWarehouse, batchCode, numberOfSerial));
    }

    @PutMapping("/api/update-serial")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> updateSerial(@Valid @RequestBody SerialDTO serialDTO){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.serialService.updateSerial(serialDTO));
    }

    @DeleteMapping("/api/delete-serial")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> deleteSerial(@NotNull @RequestParam long id){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.serialService.deleteSerial(id));
    }
}
