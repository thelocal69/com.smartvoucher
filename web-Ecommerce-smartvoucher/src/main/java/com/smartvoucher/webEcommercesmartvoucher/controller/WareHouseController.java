package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.dto.WareHouseDTO;
import com.smartvoucher.webEcommercesmartvoucher.service.IWareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warehouse")
public class WareHouseController {

    private final IWareHouseService wareHouseService;

    @Autowired
    public WareHouseController(final IWareHouseService wareHouseService) {
        this.wareHouseService = wareHouseService;
    }

    @GetMapping("")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllWareHouse() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get All warehouse success !",
                        this.wareHouseService.getAllWareHouse()
                )
        );
    }

    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertWareHouse(@RequestBody WareHouseDTO wareHouseDTO){
        List<WareHouseDTO> wareHouseDTOList = wareHouseService.getAllWareHouseCode(wareHouseDTO);
        if (wareHouseDTOList.isEmpty()){
            boolean existCategoryCodeAndDiscountCode = wareHouseService.existCategoryAndDiscount(wareHouseDTO);
            return (existCategoryCodeAndDiscountCode)
                    ? ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Insert is completed !",
                            this.wareHouseService.upsert(wareHouseDTO)
                    )
            )
                    : ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(501,
                            "Category code or discount code, not found or empty",
                            ""
                    )
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            501,
                            "Warehouse code is duplicated !",
                            ""
                    )
            );
        }
    }

    @PutMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> updateWareHouse(@RequestBody WareHouseDTO wareHouseDTO, @PathVariable Long id){
        wareHouseDTO.setId(id);
        boolean exist = wareHouseService.existWareHouse(wareHouseDTO);
        if (exist){
            boolean existCategoryCodeAndDiscountCode = wareHouseService.existCategoryAndDiscount(wareHouseDTO);
            return (existCategoryCodeAndDiscountCode)
                    ? ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200,
                            "Update is completed !",
                            this.wareHouseService.upsert(wareHouseDTO)
                    )
            )
                    : ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            501,
                            "Category code or discount code, not found or empty",
                            ""
                    )
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(
                            404,
                            "Cannot update warehouse id = "+id,
                            ""
                    )
            );
        }
    }

    @DeleteMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> deleteWareHouse(@RequestBody WareHouseDTO wareHouseDTO, @PathVariable Long id){
        wareHouseDTO.setId(id);
        if (this.wareHouseService.deleteWareHouse(wareHouseDTO)){
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
                            "Cannot delete warehouse id = "+id,
                            ""
                    )
            );
        }
    }
}
