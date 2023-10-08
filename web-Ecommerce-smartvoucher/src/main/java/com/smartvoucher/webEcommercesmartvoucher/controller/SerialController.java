package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.BaseResponse;
import com.smartvoucher.webEcommercesmartvoucher.service.impl.SerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/serial")
public class SerialController {

    private SerialService serialService;

    @Autowired
    public SerialController(SerialService serialService) {
        this.serialService = serialService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllSerial() {

       BaseResponse baseResponse = serialService.getAllSerial();

        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }

    @PostMapping("")
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> insertSerial(@RequestBody SerialDTO serialDTO) {

        BaseResponse baseResponse = serialService.insertSerial(serialDTO);


        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }

    @PutMapping("")
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> updateSerial(@RequestBody SerialDTO serialDTO) {

        BaseResponse baseResponse = serialService.updateSerial(serialDTO);

        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }

    @DeleteMapping("")
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> deleteSerial(@RequestParam long id) {

        BaseResponse baseResponse = serialService.deleteSerial(id);

        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }
}
