package com.smartvoucher.webEcommercesmartvoucher.service;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.dto.WareHouseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IWareHouseService {
    List<WareHouseDTO> getAllWareHouse();
    List<WareHouseDTO> getAllWareHouseCode(WareHouseDTO wareHouseDTO);
    WareHouseDTO upsert(WareHouseDTO wareHouseDTO);
    void deleteWareHouse(WareHouseDTO wareHouseDTO);
    Boolean existWareHouse(WareHouseDTO wareHouseDTO);
    Boolean existCategoryAndDiscountAndLabel(WareHouseDTO wareHouseDTO);
    File uploadWarehouseImages(MultipartFile fileName);
    WareHouseDTO getWarehouseById(Long id);
    List<WareHouseDTO> getAllWarehousesByLabel(int id);
}
