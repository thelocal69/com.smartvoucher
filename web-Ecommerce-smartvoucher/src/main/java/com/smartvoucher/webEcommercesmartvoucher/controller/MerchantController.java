package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;
import com.smartvoucher.webEcommercesmartvoucher.service.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

    private final IMerchantService merchantService;

    @Autowired
    public MerchantController(final IMerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping("")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllMerchant() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get All merchant success !",
                        this.merchantService.getAllMerchant()
                )
        );
    }

    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertMerchant(@RequestBody MerchantDTO merchantDTO) {
        if (!merchantService.getAllMerchantCode(merchantDTO).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            501,
                            "Merchant code is duplicated !",
                            ""
                    )
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Insert is completed !",
                            this.merchantService.upsertMerchant(merchantDTO)
                    )
            );
        }
    }

    //this is "up-sert", that mean if the instance is not existed or not found, this method is insert the new data
    @PutMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> updateMerchant(@RequestBody MerchantDTO merchantDTO, @PathVariable Long id) {
        merchantDTO.setId(id);
        boolean exist = merchantService.existMerchant(merchantDTO);
        if (exist){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Update is complete !",
                            this.merchantService.upsertMerchant(merchantDTO)
                    )
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(
                            404,
                            "Cannot update merchant id = "+id,
                            ""
                    )
            );
        }
    }

    @DeleteMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> deleteMerchant(@RequestBody MerchantDTO merchantDTO, @PathVariable Long id) {
        merchantDTO.setId(id);
        if (this.merchantService.deleteMerchant(merchantDTO)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Delete is completed !",
                            "{}"
                    )
            );
        }else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(
                            404,
                            "Cannot delete merchant id = " + id,
                            ""
                    )
            );
        }
    }
}

