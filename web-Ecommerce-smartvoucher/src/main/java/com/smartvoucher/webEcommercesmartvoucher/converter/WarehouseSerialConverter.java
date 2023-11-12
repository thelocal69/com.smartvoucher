package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WarehouseSerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.keys.WarehouseSerialKeys;
import com.smartvoucher.webEcommercesmartvoucher.repository.WarehouseSerialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WarehouseSerialConverter {

    private final WarehouseSerialRepository warehouseSerialRepository;

    @Autowired
    public WarehouseSerialConverter(WarehouseSerialRepository warehouseSerialRepository){
        this.warehouseSerialRepository = warehouseSerialRepository;
    }

    public void saveWarehouseSerial(SerialEntity serialEntity, WareHouseEntity wareHouseEntity) {
        WarehouseSerialKeys keys = new WarehouseSerialKeys();
        keys.setIdSerial(serialEntity.getId());
        keys.setIdWarehouse(wareHouseEntity.getId());
        WarehouseSerialEntity warehouseSerialEntity = new WarehouseSerialEntity();
        warehouseSerialEntity.setKeys(keys);
        warehouseSerialRepository.save(warehouseSerialEntity);
    }
}
