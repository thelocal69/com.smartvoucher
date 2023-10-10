package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.dto.DiscountTypeDTO;
import com.smartvoucher.webEcommercesmartvoucher.service.IDiscountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get All discount success !",
                        this.discountTypeService.getAllDiscount()
                )
        );
    }

    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public  ResponseEntity<ResponseObject> insertDiscount(@RequestBody DiscountTypeDTO discountTypeDTO){
        List<DiscountTypeDTO> discountTypeDTOList = discountTypeService.getAllDiscountTypeCode(discountTypeDTO);
        if (discountTypeDTOList.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Insert is completed !",
                            this.discountTypeService.upsert(discountTypeDTO)
                    )
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            501,
                            "Discount code is duplicated !",
                            ""
                    )
            );
        }
    }

    @PutMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> updateDiscount(@RequestBody DiscountTypeDTO discountTypeDTO, @PathVariable Long id){
        discountTypeDTO.setId(id);
        boolean exist = discountTypeService.existDiscount(discountTypeDTO);
        if (exist){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Update is completed !",
                            this.discountTypeService.upsert(discountTypeDTO)
                    )
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(
                            404,
                            "Cannot update discount id = "+id,
                           ""
                    )
            );
        }
    }

    @DeleteMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> deleteDiscount(@RequestBody DiscountTypeDTO discountTypeDTO, @PathVariable Long id){
        discountTypeDTO.setId(id);
        if (this.discountTypeService.deleteDiscountType(discountTypeDTO)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Delete is completed !",
                            "{}"
                    )
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(
                            404,
                            "Cannot delete discount id = "+id,
                            ""
                    )
            );
        }
    }

}
