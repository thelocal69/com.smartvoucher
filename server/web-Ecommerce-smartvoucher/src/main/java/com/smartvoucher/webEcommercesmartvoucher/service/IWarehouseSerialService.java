
package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.WarehouseSerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IWarehouseSerialService {
    List<WarehouseSerialDTO> getAllWarehouseSerial();
    ResponseOutput getAllWarehouseSerial(Long id, int page, int limit);
    WarehouseSerialDTO insert(WarehouseSerialDTO warehouseSerialDTO);
    void delete(WarehouseSerialDTO warehouseSerialDTO);

}
