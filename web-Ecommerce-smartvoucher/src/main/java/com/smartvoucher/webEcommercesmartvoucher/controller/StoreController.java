package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.dto.StoreDTO;
import com.smartvoucher.webEcommercesmartvoucher.service.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllStore() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get All store success !",
                        this.storeService.getAllStore()
                )
        );
    }
    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertStore(@RequestBody StoreDTO storeDTO){
        List<StoreDTO> storeDTOList = storeService.getAllStoreCode(storeDTO);
        if (storeDTOList.isEmpty()){
            boolean existMerchantCodeAndChainCode = storeService.existMerchantCodeAndChainCode(storeDTO);
            return  (existMerchantCodeAndChainCode)
                    ? ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Insert is completed !",
                            this.storeService.upsert(storeDTO)
                    )
            )
                    : ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            501,
                            "Merchant code or Chain code, not found or empty",
                            ""
                    )
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            501,
                            "Store code is duplicated !",
                            ""
                    )
            );
        }
    }

    @PutMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> updateStore(@RequestBody StoreDTO storeDTO, @PathVariable Long id){
        storeDTO.setId(id);
        boolean exist = storeService.existStore(storeDTO);
        if (exist){
            boolean existMerchantCodeAndChainCode = storeService.existMerchantCodeAndChainCode(storeDTO);
            return  (existMerchantCodeAndChainCode)
                    ? ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Update is completed !",
                            this.storeService.upsert(storeDTO)
                    )
            )
                    : ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            501,
                            "Merchant code or Chain code, not found or empty",
                            ""
                    )
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(
                            404,
                            "Cannot update store id = "+id,
                            ""
                    )
            );
        }
    }

    @DeleteMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> deleteStore(@RequestBody StoreDTO storeDTO, @PathVariable Long id){
        storeDTO.setId(id);
        if (this.storeService.deleteStore(storeDTO)){
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
                            "Cannot delete store id = "+id,
                            ""
                    )
            );
        }
    }
}
