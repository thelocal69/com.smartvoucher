
package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.WarehouseMerchantDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;

import java.util.List;

public interface IWarehouseMerchantService {

    List<WarehouseMerchantDTO> getAllWarehouseMerchant();
    WarehouseMerchantDTO insert(WarehouseMerchantDTO warehouseMerchantDTO);
    void delete(WarehouseMerchantDTO warehouseMerchantDTO);

}
