
package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.WarehouseSerialConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.WarehouseSerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WarehouseMerchantEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WarehouseSerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.keys.WarehouseSerialKeys;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.IWareHouseRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.SerialRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.WarehouseSerialRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IWarehouseSerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WarehouseSerialService implements IWarehouseSerialService {
    private WarehouseSerialRepository warehouseSerialRepository;
    private IWareHouseRepository wareHouseRepository;
    private SerialRepository serialRepository;
    private WarehouseSerialConverter warehouseSerialConverter;

    @Autowired
    public WarehouseSerialService(WarehouseSerialRepository warehouseSerialRepository,
                                  IWareHouseRepository wareHouseRepository,
                                  SerialRepository serialRepository,
                                  WarehouseSerialConverter warehouseSerialConverter) {
        this.warehouseSerialRepository = warehouseSerialRepository;
        this.wareHouseRepository = wareHouseRepository;
        this.serialRepository = serialRepository;
        this.warehouseSerialConverter = warehouseSerialConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseSerialDTO> getAllWarehouseSerial(){
        List<WarehouseSerialEntity> list = warehouseSerialRepository.getAllWarehouseSerial();
        return warehouseSerialConverter.toWarehouseSerialDTOList(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WarehouseSerialDTO insert(WarehouseSerialDTO warehouseSerialDTO){
        WarehouseSerialEntity warehouseSerialEntity = warehouseSerialConverter.toWarehouseSerialEntity(warehouseSerialDTO);
        if(warehouseSerialDTO.getKeys()!=null){
            if(wareHouseRepository.findAllByWarehouseCode(warehouseSerialDTO.getWarehouseCode()).size()<1
                || serialRepository.findBySerialCode(warehouseSerialDTO.getSerialCode())==null){
                throw new ObjectNotFoundException(
                        406, "WarehouseCode or SerialCode is not exist!"
                );
            }
            else if(wareHouseRepository.findAllByWarehouseCode(warehouseSerialDTO.getWarehouseCode()).size()<1
                && serialRepository.findBySerialCode(warehouseSerialDTO.getSerialCode())==null){
                throw new ObjectNotFoundException(
                        406, "WarehouseCode and SerialCode is not exist!"
                );
            }
        }else{
            WareHouseEntity wareHouseEntity = wareHouseRepository.findOneByWarehouseCode(warehouseSerialDTO.getWarehouseCode());
            warehouseSerialEntity.setIdWarehouse(wareHouseEntity);
            SerialEntity serialEntity = serialRepository.findBySerialCode(warehouseSerialDTO.getSerialCode());
            warehouseSerialEntity.setIdSerial(serialEntity);
            warehouseSerialEntity.setCreatedBy(warehouseSerialDTO.getCreatedBy());
            warehouseSerialEntity.setCreatedAt(warehouseSerialDTO.getCreatedAt());
            warehouseSerialEntity.setUpdateBy(warehouseSerialDTO.getUpdatedBy());
            warehouseSerialEntity.setUpdateAt(warehouseSerialDTO.getUpdatedAt());
            WarehouseSerialKeys keys = warehouseSerialConverter.toWarehouseSerialKeys(warehouseSerialDTO);
            keys.setIdWarehouse(wareHouseEntity.getId());
            keys.setIdSerial(serialEntity.getId());
            warehouseSerialEntity.setKeys(keys);
        }
        return warehouseSerialConverter.toWarehouseSerialDTO(warehouseSerialRepository.save(warehouseSerialEntity));
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(WarehouseSerialDTO warehouseSerialDTO){
        WarehouseSerialEntity warehouseSerialCheck = warehouseSerialRepository.getByWarehouseCodeAndSerialCode(warehouseSerialDTO.getWarehouseCode(),
                warehouseSerialDTO.getSerialCode());
        if(warehouseSerialCheck==null){
            throw new ObjectNotFoundException(
                    404, "Cannot delete keys " + warehouseSerialDTO.getKeys()
            );
        }
        this.warehouseSerialRepository.deleteByKeys(warehouseSerialCheck.getKeys());
    }



}
