package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.baseResponse.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.dto.StoreDTO;
import com.smartvoucher.webEcommercesmartvoucher.service.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {

    private final IStoreService storeService;

    @Autowired
    public StoreController(final IStoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllStore() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "Success",
                        "Get All store success !",
                        this.storeService.getAllStore()
                )
        );
    }
    @PostMapping("/api/insert")
    public ResponseEntity<ResponseObject> insertStore(@RequestBody StoreDTO storeDTO){
        List<StoreDTO> storeDTOList = storeService.getAllStoreCode(storeDTO);
        if (storeDTOList.isEmpty()){
            boolean existMerchantCodeAndChainCode = storeService.existMerchantCodeAndChainCode(storeDTO);
            return  (existMerchantCodeAndChainCode)
                    ? ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            "Success",
                            "Insert is completed !",
                            this.storeService.upsert(storeDTO)
                    )
            )
                    : ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            "Failed",
                            "Merchant code or Chain code, not found or empty",
                            ""
                    )
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            "Failed",
                            "Store code is duplicated !",
                            ""
                    )
            );
        }
    }

    @PutMapping("/api/{id}")
    public ResponseEntity<ResponseObject> updateStore(@RequestBody StoreDTO storeDTO, @PathVariable Long id){
        storeDTO.setId(id);
        boolean exist = storeService.existStore(storeDTO);
        if (exist){
            boolean existMerchantCodeAndChainCode = storeService.existMerchantCodeAndChainCode(storeDTO);
            return  (existMerchantCodeAndChainCode)
                    ? ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            "Success",
                            "Update is completed !",
                            this.storeService.upsert(storeDTO)
                    )
            )
                    : ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            "Failed",
                            "Merchant code or Chain code, not found or empty",
                            ""
                    )
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(
                            "Failed",
                            "Cannot update store id = "+id,
                            ""
                    )
            );
        }
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<ResponseObject> deleteStore(@RequestBody StoreDTO storeDTO, @PathVariable Long id){
        storeDTO.setId(id);
        if (this.storeService.deleteStore(storeDTO)){
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
                            "Cannot delete store id = "+id,
                            ""
                    )
            );
        }
    }
}
