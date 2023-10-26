package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.ISerialService;
import com.smartvoucher.webEcommercesmartvoucher.service.impl.SerialService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllSerial(){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.serialService.getAllSerial());
    }

    @PostMapping("")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertSerial(@Valid @RequestBody SerialDTO serialDTO){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.serialService.insertSerial(serialDTO));
    }

    @PutMapping("")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> updateSerial(@Valid @RequestBody SerialDTO serialDTO){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.serialService.updateSerial(serialDTO));
    }

    @DeleteMapping("")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> deleteSerial(@NotNull @RequestParam long id){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.serialService.deleteSerial(id));
    }
}
