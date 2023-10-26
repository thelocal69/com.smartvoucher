package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("label")
public class LabelController {

    private final ILabelService labelService;

    @Autowired
    public LabelController(final ILabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping("/api/getAll")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllLabel(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get all label name succesfully !",
                        labelService.getAllLabel()
                )
        );
    }
}
