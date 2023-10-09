package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.WareHouseDTO;

import java.util.List;

public interface IWareHouseService {
    List<WareHouseDTO> getAllWareHouse();
    List<WareHouseDTO> getAllWareHouseCode(WareHouseDTO wareHouseDTO);
    WareHouseDTO upsert(WareHouseDTO wareHouseDTO);
    Boolean deleteWareHouse(WareHouseDTO wareHouseDTO);
    Boolean existWareHouse(WareHouseDTO wareHouseDTO);
    Boolean existCategoryAndDiscount(WareHouseDTO wareHouseDTO);
}
