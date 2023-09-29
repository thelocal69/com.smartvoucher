package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.baseResponse.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.dto.WareHouseDTO;
import com.smartvoucher.webEcommercesmartvoucher.service.IWareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseObject> getAllWareHouse() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "Success",
                        "Get All warehouse success !",
                        this.wareHouseService.getAllWareHouse()
                )
        );
    }

    @PostMapping("/api/insert")
    public ResponseEntity<ResponseObject> insertWareHouse(@RequestBody WareHouseDTO wareHouseDTO){
        List<WareHouseDTO> wareHouseDTOList = wareHouseService.getAllWareHouseCode(wareHouseDTO);
        if (wareHouseDTOList.isEmpty()){
            boolean existCategoryCodeAndDiscountCode = wareHouseService.existCategoryAndDiscount(wareHouseDTO);
            return (existCategoryCodeAndDiscountCode)
                    ? ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            "Success",
                            "Insert is completed !",
                            this.wareHouseService.upsert(wareHouseDTO)
                    )
            )
                    : ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            "Failed",
                            "Category code or discount code, not found or empty",
                            ""
                    )
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            "Failed",
                            "Warehouse code is duplicated !",
                            ""
                    )
            );
        }
    }

    @PutMapping("/api/{id}")
    public ResponseEntity<ResponseObject> updateWareHouse(@RequestBody WareHouseDTO wareHouseDTO, @PathVariable Long id){
        wareHouseDTO.setId(id);
        boolean exist = wareHouseService.existWareHouse(wareHouseDTO);
        if (exist){
            boolean existCategoryCodeAndDiscountCode = wareHouseService.existCategoryAndDiscount(wareHouseDTO);
            return (existCategoryCodeAndDiscountCode)
                    ? ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            "Success",
                            "Update is completed !",
                            this.wareHouseService.upsert(wareHouseDTO)
                    )
            )
                    : ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            "Failed",
                            "Category code or discount code, not found or empty",
                            ""
                    )
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(
                            "Failed",
                            "Cannot update warehouse id = "+id,
                            ""
                    )
            );
        }
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<ResponseObject> deleteWareHouse(@RequestBody WareHouseDTO wareHouseDTO, @PathVariable Long id){
        wareHouseDTO.setId(id);
        if (this.wareHouseService.deleteWareHouse(wareHouseDTO)){
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
                            "Cannot delete warehouse id = "+id,
                            ""
                    )
            );
        }
    }
}
