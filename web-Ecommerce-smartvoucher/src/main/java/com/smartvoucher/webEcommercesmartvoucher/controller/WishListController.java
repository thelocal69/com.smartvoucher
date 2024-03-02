package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.WishListDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.service.IWishListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/wishlist")
public class WishListController {

    private final IWishListService wishListService;

    @Autowired
    public WishListController(final IWishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping("/api/get_all")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseOutput> getAllWishList(
            @RequestParam Long idUser,
            @RequestParam int page,
            @RequestParam int limit
    ){
        log.info("Get All Wishlist is completed!");
        return new ResponseEntity<>(
                this.wishListService.getAllWishListByIdUser(
                        idUser, page, limit),
                HttpStatus.OK
        );
    }

    @GetMapping("/api/get")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getWishList(
            @RequestParam Long idUser,
            @RequestParam Long idWarehouse
    ){
        log.info("Get Wishlist is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get Wishlist is completed !",
                        this.wishListService.getWishList(idUser, idWarehouse)
                )
        );
    }

    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertWishList(
            @RequestBody WishListDTO wishListDTO
            ){
        log.info("Insert completed");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Insert completed",
                        this.wishListService.insert(wishListDTO)
                )
        );
    }

    @DeleteMapping("/api/delete/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> deleteWishList(
            @RequestBody WishListDTO wishListDTO,
            @PathVariable Long id)
    {
        wishListDTO.setId(id);
        this.wishListService.deleteWishList(wishListDTO);
        log.info("Delete completed");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Delete completed",
                        "{}"
                )
        );
    }
}
