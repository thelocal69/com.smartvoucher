package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.service.IDiscountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discount")
public class DiscountTypeController {

    private final IDiscountTypeService discountTypeService;

    @Autowired
    public DiscountTypeController(final IDiscountTypeService discountTypeService) {
        this.discountTypeService = discountTypeService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllDiscount(){
        return new ResponseEntity<>(discountTypeService.getAllDiscount(), HttpStatus.OK);
    }
}
