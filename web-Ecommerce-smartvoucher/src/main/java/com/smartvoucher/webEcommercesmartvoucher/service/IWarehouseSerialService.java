
package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.WarehouseSerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;

import java.util.List;

public interface IWarehouseSerialService {
    List<WarehouseSerialDTO> getAllWarehouseSerial();
    WarehouseSerialDTO insert(WarehouseSerialDTO warehouseSerialDTO);
    void delete(WarehouseSerialDTO warehouseSerialDTO);

}
