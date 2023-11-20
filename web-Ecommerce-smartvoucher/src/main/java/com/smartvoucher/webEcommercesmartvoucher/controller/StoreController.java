package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.StoreDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

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
    public ResponseEntity<ResponseObject> insertStore(@Valid @RequestBody StoreDTO storeDTO){
        storeDTO.setStoreCode(
                UUID.randomUUID()
                        .toString()
                        .replace("-","")
                        .substring(0,20)
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Insert is completed !",
                            this.storeService.upsert(storeDTO)
                    )
            );
    }

    @PutMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> updateStore(@Valid  @RequestBody StoreDTO storeDTO, @PathVariable Long id){
        storeDTO.setId(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Update is completed !",
                            this.storeService.upsert(storeDTO)
                    )
            );
    }

    @DeleteMapping("/api/delete-store")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> deleteStore(@RequestParam Long id){
//        storeDTO.setId(id);
        this.storeService.deleteStore(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Delete is completed !",
                            "{}"
                    )
            );
    }
}
