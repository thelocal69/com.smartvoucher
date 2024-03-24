
package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.WarehouseStoreDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.WarehouseStoreEntity;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;

import java.util.List;

public interface IWarehouseStoreService {
    List<WarehouseStoreDTO> getAllWarehouseStore();
    WarehouseStoreDTO insert(WarehouseStoreDTO warehouseStoreDTO);
    void delete(WarehouseStoreDTO warehouseStoreDTO);
    WarehouseStoreDTO getWarehouseStoreByIdWarehouse(Long idWarehouse);
}
