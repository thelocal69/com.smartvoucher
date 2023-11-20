package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.WarehouseMerchantConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.WarehouseMerchantDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.MerchantEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.RoleEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WarehouseMerchantEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.keys.WarehouseMerchantKeys;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.repository.IMerchantRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.IWareHouseRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.RoleRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.WarehouseMerchantRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IWarehouseMerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class WarehouseMerchantService implements IWarehouseMerchantService {
    private final WarehouseMerchantRepository warehouseMerchantRepository;
    private final IWareHouseRepository wareHouseRepository;
    private final IMerchantRepository merchantRepository;
    private final RoleRepository roleRepository;
    private final WarehouseMerchantConverter warehouseMerchantConverter;

    @Autowired
    public WarehouseMerchantService(final WarehouseMerchantRepository warehouseMerchantRepository,
                                    final IWareHouseRepository wareHouseRepository,
                                    final IMerchantRepository merchantRepository,
                                    final RoleRepository roleRepository,
                                    final WarehouseMerchantConverter warehouseMerchantConverter) {
        this.warehouseMerchantRepository = warehouseMerchantRepository;
        this.wareHouseRepository = wareHouseRepository;
        this.merchantRepository = merchantRepository;
        this.roleRepository = roleRepository;
        this.warehouseMerchantConverter = warehouseMerchantConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseMerchantDTO> getAllWarehouseMerchant(){
        List<WarehouseMerchantEntity> list = warehouseMerchantRepository.getAllWarehouseMerchant();
        log.info("Get all warehouseMerchant completed !");
        return warehouseMerchantConverter.toWarehouseMerchantDTOList(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WarehouseMerchantDTO insert(WarehouseMerchantDTO warehouseMerchantDTO){
        WarehouseMerchantEntity warehouseMerchantEntity = warehouseMerchantConverter.toWarehouseMerchantEntity(warehouseMerchantDTO);
        if(warehouseMerchantDTO.getKeys()!= null){
            if(wareHouseRepository.findAllByWarehouseCode(warehouseMerchantDTO.getWarehouseCode()).isEmpty()
                    || merchantRepository.findAllByMerchantCode(warehouseMerchantDTO.getMerchantCode()).isEmpty()){
                log.info("WarehouseCode is not exist or MerchantCode is not exist!");
                throw new ObjectNotFoundException(
                        406,"WarehouseCode is not exist or MerchantCode is not exist!"
                );
            }
            else if(wareHouseRepository.findAllByWarehouseCode(warehouseMerchantDTO.getWarehouseCode()).isEmpty()
                    && merchantRepository.findAllByMerchantCode(warehouseMerchantDTO.getMerchantCode()).isEmpty()){
                log.info("WarehouseCode and MerchantCode is not exist");
                throw new ObjectNotFoundException(
                        406, "WarehouseCode and MerchantCode is not exist"
                );
            }
        }else{
            MerchantEntity merchantEntity = merchantRepository.findOneByMerchantCode(warehouseMerchantDTO.getMerchantCode());
            warehouseMerchantEntity.setIdMerchant(merchantEntity);
            WareHouseEntity wareHouseEntity = wareHouseRepository.findOneByWarehouseCode(warehouseMerchantDTO.getWarehouseCode());
            warehouseMerchantEntity.setIdWarehouse(wareHouseEntity);
            RoleEntity roleEntity = roleRepository.findOneByRoleCode(warehouseMerchantDTO.getRoleCode());

            //set keys
            WarehouseMerchantKeys keys = warehouseMerchantConverter.toWarehouseMerchantKeys(warehouseMerchantDTO);
            keys.setIdMerchant(merchantEntity.getId());
            keys.setIdWarehouse(wareHouseEntity.getId());
            warehouseMerchantEntity.setKeys(keys);
            warehouseMerchantEntity.setIdRole(roleEntity);
            log.info("Insert warehouseMerchant is completed !");
        }
        return warehouseMerchantConverter.toWarehouseMerchantDTO(warehouseMerchantRepository.save(warehouseMerchantEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(WarehouseMerchantDTO warehouseMerchantDTO){

        WarehouseMerchantEntity warehouseMerchantCheck = warehouseMerchantRepository.getByWarehouseCodeAndMerchantCode(warehouseMerchantDTO.getWarehouseCode(),
                warehouseMerchantDTO.getMerchantCode());
        if(warehouseMerchantCheck == null){
            log.info("Cannot delete keys " + warehouseMerchantDTO.getKeys());
            throw new ObjectNotFoundException(
                    404, "Cannot delete keys " + warehouseMerchantDTO.getKeys()
            );
        }
        //this.warehouseMerchantRepository.deleteById(warehouseMerchantDTO.getKeys());
        log.info("Delete warehouseMerchant is completed !");
        this.warehouseMerchantRepository.deleteByKeys(warehouseMerchantCheck.getKeys());
    }


}
