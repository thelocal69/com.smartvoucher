package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.service.IWareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/warehouse")
public class WareHouseController {

    private final IWareHouseService wareHouseService;

    @Autowired
    public WareHouseController(final IWareHouseService wareHouseService) {
        this.wareHouseService = wareHouseService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllWareHouse(){
        return new ResponseEntity<>(wareHouseService.getAllWareHouse(), HttpStatus.OK);
    }
}
