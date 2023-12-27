package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.service.ILabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("label")
public class LabelController {

    private final ILabelService labelService;

    @Autowired
    public LabelController(final ILabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping("/api/all")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllLabel(){
        log.info("Get all label name successfully !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get all label name successfully !",
                        labelService.getAllLabel()
                )
        );
    }

    @GetMapping("/api/getName")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllLabelName(){
        log.info("Get all label name successfully !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get all label name successfully !",
                        labelService.getAllNameByLabel()
                )
        );
    }

    @GetMapping("/api/getAll")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseOutput> getAllLabel(
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam String sortBy,
            @RequestParam String sortField
    ){
        log.info("Get all label successfully !");
        return new ResponseEntity<>(labelService.getAllLabel(page, limit, sortBy, sortField), HttpStatus.OK);
    }
}
