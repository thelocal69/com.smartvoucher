package com.smartvoucher.webEcommercesmartvoucher.service;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.dto.WareHouseDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IWareHouseService {
    List<WareHouseDTO> getAllWareHouse();
    ResponseOutput getAllWareHouse(int page, int limit, String sortBy, String sortField);
    List<WareHouseDTO> getAllWareHouseCode(WareHouseDTO wareHouseDTO);
    List<WareHouseDTO> searchByWarehouseName(String name);
    WareHouseDTO upsert(WareHouseDTO wareHouseDTO);
    void deleteWareHouse(WareHouseDTO wareHouseDTO);
    Boolean existWareHouse(WareHouseDTO wareHouseDTO);
    Boolean existCategoryAndDiscountAndLabel(WareHouseDTO wareHouseDTO);
    String uploadWarehouseImages(MultipartFile fileName);
    WareHouseDTO getWarehouseById(Long id);
    List<WareHouseDTO> getAllWarehousesByLabel(int id);
    ResponseOutput getAllWarehousesByLabel(String  id, int page, int limit);
    List<WareHouseDTO> getAllWarehouseByCategoryId(long id);
}
