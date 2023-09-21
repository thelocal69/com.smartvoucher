package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.baseResponse.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;
import com.smartvoucher.webEcommercesmartvoucher.service.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseObject> getAllMerchant(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "Success",
                        "Get All merchant success !",
                        merchantService.getAllMerchant()
                )
        );
    }

    @PostMapping("/api/insert")
    public ResponseEntity<ResponseObject> insertMerchant(@RequestBody MerchantDTO merchantDTO){
        if (!merchantService.getAllMerchantCode(merchantDTO).isEmpty()){
           return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                   new ResponseObject(
                           "Failed",
                           "Merchant code is duplicated !",
                           ""
                   )
           );
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            "Success",
                            "Insert is completed !",
                            merchantService.insertMerchant(merchantDTO)
                    )
            );
        }
    }

    //this is "up-sert", that mean if the instance is not existed or not found, this method is insert the new data
    @PutMapping("/api/{id}")
    public ResponseEntity<?> updateMerchant(@RequestBody MerchantDTO merchantDTO, @PathVariable Long id){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            "Success",
                            "Update is completed !",
                            merchantService.updateMerchant(merchantDTO, id)
                    )
            );
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<ResponseObject> deleteMerchant(@PathVariable Long id){
        if (merchantService.deleteMerchant(id)){
            this.deleteMerchant(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            "Success",
                            "Delete is completed !",
                            "{}"
                    )
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(
                            "Failed",
                            "Cannot delete merchant id = "+ id,
                            ""
                    )
            );
        }
    }
}
