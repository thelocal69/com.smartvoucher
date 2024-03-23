package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.WishListDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;

public interface IWishListService {
    ResponseOutput getAllWishListByIdUser(Long idUser, int page, int limit);
    WishListDTO insert(WishListDTO wishListDTO);
    WishListDTO getWishList(Long idUser, Long idWarehouse);
    void deleteWishList(WishListDTO wishListDTO);
}
