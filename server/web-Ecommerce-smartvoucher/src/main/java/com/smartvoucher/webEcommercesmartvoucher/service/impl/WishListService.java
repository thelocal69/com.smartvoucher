package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.WishListConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.WishListDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WishListEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.enums.Provider;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.repository.IWareHouseRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.IWishListRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IWishListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class WishListService implements IWishListService {

    private final IWishListRepository iWishListRepository;
    private final UserRepository userRepository;
    private final IWareHouseRepository iWareHouseRepository;
    private final WishListConverter wishListConverter;

    @Autowired
    public WishListService(final IWishListRepository iWishListRepository,
                           final UserRepository userRepository,
                           final IWareHouseRepository iWareHouseRepository,
                           final WishListConverter wishListConverter) {
        this.iWishListRepository = iWishListRepository;
        this.userRepository = userRepository;
        this.iWareHouseRepository = iWareHouseRepository;
        this.wishListConverter = wishListConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseOutput getAllWishListByIdUser(Long idUser, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        List<WishListEntity> wishListEntityList = iWishListRepository.findAllByIdUser_Id(idUser, pageable);
        if (wishListEntityList.isEmpty()){
            throw new ObjectEmptyException(500, "List Wishlist is empty !");
        }
        List<WishListDTO> wishListDTOList = wishListConverter.toWishListDTOList(wishListEntityList);
        int totalItem = iWishListRepository.countByIdUser(idUser);
        int totalPage = (int)Math.ceil((double) totalItem / limit);
        log.info("Get list wishlist is successfully !");
        return new ResponseOutput(
                page,
                totalItem,
                totalPage,
                wishListDTOList
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WishListDTO insert(WishListDTO wishListDTO) {
        UserEntity userEntity = userRepository.findOneByIdAndProvider(wishListDTO.getIdUser(), Provider.local.name());
        WareHouseEntity wareHouseEntity = iWareHouseRepository.findOneById(wishListDTO.getIdWarehouse());
        if (userEntity == null || wareHouseEntity == null){
            log.info("Warehouse is not exist or User is not exist!");
            throw new ObjectNotFoundException(
                    404,"Warehouse is not exist or User is not exist!"
            );
        }
        if (iWishListRepository.findByIdUser_IdAndIdWarehouse_Id(
            wishListDTO.getIdUser(), wishListDTO.getIdWarehouse()
        ) != null){
            log.info("Wishlist is exist!");
            throw new DuplicationCodeException(500, "Wishlist is exist !");
        }
        WishListEntity wishListEntity = wishListConverter.toWishListEntity(wishListDTO, wareHouseEntity);
        wishListEntity.setIdUser(userEntity);
        wishListEntity.setIdWarehouse(wareHouseEntity);
        this.iWishListRepository.save(wishListEntity);
        log.info("Insert wishlist is completed !");
        return wishListConverter.toWishListDTO(wishListEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public WishListDTO getWishList(Long idUser, Long idWarehouse) {
        WishListEntity wishListEntity = iWishListRepository.findByIdUser_IdAndIdWarehouse_Id(
                idUser, idWarehouse
        );
        if (wishListEntity == null){
            throw new ObjectNotFoundException(404, "Warehouse and User is not found !");
        }
        return wishListConverter.toWishListDTO(wishListEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteWishList(WishListDTO wishListDTO) {
        WishListEntity wishListEntity = iWishListRepository.findOneById(wishListDTO.getId());
        if (wishListEntity == null){
            log.info("Cannot delete wishlist !");
            throw new ObjectNotFoundException(
                    404,"Warehouse is not exist or User is not exist!"
            );
        }
        log.info("Delete wishlist is successfully !");
        this.iWishListRepository.deleteById(wishListEntity.getId());
    }
}
