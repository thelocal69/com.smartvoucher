package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.DiscountTypeDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.service.IDiscountTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/discount")
public class DiscountTypeController {

    private final IDiscountTypeService discountTypeService;

    @Autowired
    public DiscountTypeController(final IDiscountTypeService discountTypeService) {
        this.discountTypeService = discountTypeService;
    }

    @GetMapping("")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllDiscount() {
        log.info("Get All discount success !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get All discount success !",
                        this.discountTypeService.getAllDiscount()
                )
        );
    }

    @GetMapping("/api/getName")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllDiscountName() {
        log.info("Get All discount name success !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get All discount name success !",
                        this.discountTypeService.getAllNameByDiscount()
                )
        );
    }

    @GetMapping("/api/search")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> searchAllByName(@RequestParam String name) {
        log.info("Search All discount success !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Search All discount success !",
                        this.discountTypeService.searchByName(name)
                )
        );
    }

    @GetMapping("/api/getAll")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseOutput> getAllDiscount(
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam String sortBy,
            @RequestParam String sortField
    ) {
        log.info("Get All discount success !");
        return new ResponseEntity<>(discountTypeService.getAllDiscountType(
                page, limit, sortBy, sortField), HttpStatus.OK);
    }

    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public  ResponseEntity<ResponseObject> insertDiscount(@Valid @RequestBody DiscountTypeDTO discountTypeDTO){
        discountTypeDTO.setCode("D-"+
                UUID.randomUUID()
                        .toString()
                        .replace("-","")
                        .substring(0,18)
        );
        log.info("Insert is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Insert is completed !",
                            this.discountTypeService.upsert(discountTypeDTO)
                    )
            );
    }

    @PutMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> updateDiscount(@Valid  @RequestBody DiscountTypeDTO discountTypeDTO, @PathVariable Long id){
        discountTypeDTO.setId(id);
        log.info("Update is completed !");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Update is completed !",
                            this.discountTypeService.upsert(discountTypeDTO)
                    )
            );
    }

    @DeleteMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> deleteDiscount(@RequestBody DiscountTypeDTO discountTypeDTO, @PathVariable Long id) {
        discountTypeDTO.setId(id);
        this.discountTypeService.deleteDiscountType(discountTypeDTO);
        log.info("Delete is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Delete is completed !",
                        "{}"
                )
        );
    }
}
