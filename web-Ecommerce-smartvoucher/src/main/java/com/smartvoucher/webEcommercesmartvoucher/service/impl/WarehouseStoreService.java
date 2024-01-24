
package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.WarehouseStoreConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.WarehouseStoreDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.StoreEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WarehouseStoreEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.keys.WarehouseStoreKeys;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.repository.IStoreRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.IWareHouseRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.WarehouseStoreRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IWarehouseStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class WarehouseStoreService implements IWarehouseStoreService {
    private WarehouseStoreRepository warehouseStoreRepository;
    private IWareHouseRepository wareHouseRepository;
    private IStoreRepository storeRepository;
    private WarehouseStoreConverter warehouseStoreConverter;
    @Autowired
    public WarehouseStoreService(WarehouseStoreRepository warehouseStoreRepository,
                                 IWareHouseRepository wareHouseRepository,
                                 IStoreRepository storeRepository,
                                 WarehouseStoreConverter warehouseStoreConverter) {
        this.warehouseStoreRepository = warehouseStoreRepository;
        this.wareHouseRepository = wareHouseRepository;
        this.storeRepository = storeRepository;
        this.warehouseStoreConverter = warehouseStoreConverter;
    }
    @Override
    @Transactional(readOnly = true)
    public List<WarehouseStoreDTO> getAllWarehouseStore(){
        List<WarehouseStoreEntity> list = warehouseStoreRepository.getAllWarehouseStore();
        return warehouseStoreConverter.toWarehouseStoreDTOList(list);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public WarehouseStoreDTO insert(WarehouseStoreDTO warehouseStoreDTO){
        WarehouseStoreEntity warehouseStoreEntity = warehouseStoreConverter.toWarehouseStoreEntity(warehouseStoreDTO);
        if(warehouseStoreDTO.getKeys()!=null){
            if(wareHouseRepository.findAllByWarehouseCode(warehouseStoreDTO.getWarehouseCode()).size()<1
            || storeRepository.findAllByStoreCode(warehouseStoreDTO.getStoreCode()).size()<1){
                log.info("WarehouseCode or StoreCode is not exist!");
                throw new ObjectNotFoundException(
                        404, "WarehouseCode or StoreCode is not exist!"
                );
            }else if(wareHouseRepository.findAllByWarehouseCode(warehouseStoreDTO.getWarehouseCode()).size()<1
            && storeRepository.findAllByStoreCode(warehouseStoreDTO.getStoreCode()).size()<1){
                log.info("WarehouseCode and StoreCode is not exist!");
                throw new ObjectNotFoundException(
                        404, "WarehouseCode and StoreCode is not exist!"
                );
            }
        }else{
            WareHouseEntity wareHouseEntity = wareHouseRepository.findOneByWarehouseCode(warehouseStoreDTO.getWarehouseCode());
            warehouseStoreEntity.setIdWarehouse(wareHouseEntity);
            StoreEntity storeEntity = storeRepository.findOneByStoreCode(warehouseStoreDTO.getStoreCode());
            warehouseStoreEntity.setIdStore(storeEntity);
            warehouseStoreEntity.setUpdateAt(warehouseStoreDTO.getUpdatedAt());
            warehouseStoreEntity.setCreatedAt(warehouseStoreDTO.getCreatedAt());
            warehouseStoreEntity.setCreatedBy(warehouseStoreDTO.getCreatedBy());
            warehouseStoreEntity.setUpdateBy(warehouseStoreDTO.getUpdatedBy());
            WarehouseStoreKeys keys = warehouseStoreConverter.toWarehouseStoreKeys(warehouseStoreDTO);
            keys.setIdStore(storeEntity.getId());
            keys.setIdWarehouse(wareHouseEntity.getId());
            warehouseStoreEntity.setKeys(keys);
            log.info("Insert warehouseStore is completed !");
        }
        return warehouseStoreConverter.toWarehouseStoreDTO(warehouseStoreRepository.save(warehouseStoreEntity));
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(WarehouseStoreDTO warehouseStoreDTO){
        WarehouseStoreEntity warehouseStoreCheck = warehouseStoreRepository.getByWarehouseCodeAndStoreCode(warehouseStoreDTO.getWarehouseCode(), warehouseStoreDTO.getStoreCode());
        if(warehouseStoreCheck==null){
            log.info("Cannot delete keys " + warehouseStoreDTO.getKeys());
            throw new ObjectNotFoundException(
                    404, "Cannot delete keys " + warehouseStoreDTO.getKeys()
            );
        }
        log.info("Delete warehouseStore is completed !");
        this.warehouseStoreRepository.deleteByKeys(warehouseStoreCheck.getKeys());
    }

    @Override
    public WarehouseStoreDTO getWarehouseStoreByIdWarehouse(Long idWarehouse) {
        WarehouseStoreEntity warehouseStoreEntity = warehouseStoreRepository.getWarehouseStoreEntitiesByIdWarehouse(
                idWarehouse
        );
        if (warehouseStoreEntity == null){
            log.info("Id store or warehouse not found !");
            throw new ObjectNotFoundException(404, "Id store or warehouse not found !");
        }
        return warehouseStoreConverter.toWarehouseStoreDTO(warehouseStoreEntity);
    }


}
