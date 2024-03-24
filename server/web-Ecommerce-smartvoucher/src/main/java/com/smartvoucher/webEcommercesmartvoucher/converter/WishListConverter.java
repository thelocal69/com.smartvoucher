package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.WishListDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WishListEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WishListConverter {
   public WishListDTO toWishListDTO(WishListEntity wishListEntity){
        WishListDTO wishListDTO = new WishListDTO();
        wishListDTO.setId(wishListEntity.getId());
        wishListDTO.setIdUser(wishListEntity.getIdUser().getId());
        wishListDTO.setIdWarehouse(wishListEntity.getIdWarehouse().getId());
        wishListDTO.setName(wishListEntity.getIdWarehouse().getName());
        wishListDTO.setBannerUrl(wishListEntity.getIdWarehouse().getBannerUrl());
        wishListDTO.setPrice(wishListEntity.getIdWarehouse().getPrice());
        wishListDTO.setCategoryName(wishListEntity.getIdWarehouse().getCategory().getName());
        wishListDTO.setOriginalPrice(wishListEntity.getIdWarehouse().getOriginalPrice());
        wishListDTO.setMaxDiscountAmount(wishListEntity.getIdWarehouse().getMaxDiscountAmount());
        wishListDTO.setStatus(wishListEntity.getStatus());
        return wishListDTO;
    }

    public List<WishListDTO> toWishListDTOList(List<WishListEntity> wishListEntityList){
       return wishListEntityList.stream().map(this::toWishListDTO).collect(Collectors.toList());
    }

    public WishListEntity toWishListEntity(WishListDTO wishListDTO ,WareHouseEntity wareHouseEntity){
       WishListEntity wishListEntity = new WishListEntity();

       wishListDTO.setName(wareHouseEntity.getName());
       wishListDTO.setPrice(wareHouseEntity.getPrice());
       wishListDTO.setBannerUrl(wareHouseEntity.getBannerUrl());
       wishListDTO.setOriginalPrice(wareHouseEntity.getOriginalPrice());
       wishListDTO.setCategoryName(wareHouseEntity.getCategory().getName());
       wishListDTO.setMaxDiscountAmount(wareHouseEntity.getMaxDiscountAmount());
       wishListDTO.setStatus(wareHouseEntity.getStatus());

       wishListEntity.setName(wishListDTO.getName());
       wishListEntity.setPrice(wishListDTO.getPrice());
       wishListEntity.setOriginalPrice(wishListDTO.getOriginalPrice());
       wishListEntity.setBannerUrl(wishListDTO.getBannerUrl());
       wishListEntity.setCategoryName(wishListDTO.getCategoryName());
       wishListEntity.setMaxDiscountAmount(wishListDTO.getMaxDiscountAmount());
       wishListEntity.setStatus(wishListDTO.getStatus());
       return  wishListEntity;
    }
}

