package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.service.ISerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/serial")
public class SerialController {

    private final ISerialService serialService;

    @Autowired
    public SerialController(final ISerialService serialService) {
        this.serialService = serialService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllSerial(){
        return new ResponseEntity<>(serialService.getAllSerial(), HttpStatus.OK);
    }
}
