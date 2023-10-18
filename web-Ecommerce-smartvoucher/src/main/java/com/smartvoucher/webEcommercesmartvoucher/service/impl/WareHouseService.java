package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.WareHouseConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.WareHouseDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.CategoryEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.DiscountTypeEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.repository.ICategoryRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.IDiscountTypeRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.IWareHouseRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IWareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WareHouseService implements IWareHouseService {

    private final IWareHouseRepository wareHouseRepository;
    private final WareHouseConverter wareHouseConverter;
    private final IDiscountTypeRepository discountTypeRepository;
    private final ICategoryRepository categoryRepository;

    @Autowired
    public WareHouseService(final IWareHouseRepository wareHouseRepository,
                            final WareHouseConverter wareHouseConverter,
                            final IDiscountTypeRepository discountTypeRepository,
                            final ICategoryRepository categoryRepository
    ) {
        this.wareHouseRepository = wareHouseRepository;
        this.wareHouseConverter = wareHouseConverter;
        this.discountTypeRepository = discountTypeRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WareHouseDTO> getAllWareHouse() {
        List<WareHouseEntity> wareHouseEntityList = wareHouseRepository.findAll();
        if (wareHouseEntityList.isEmpty()){
            throw new ObjectEmptyException(
                    404, "List warehouse is empty !"
            );
        }
        return wareHouseConverter.toWareHouseDTOList(wareHouseEntityList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WareHouseDTO> getAllWareHouseCode(WareHouseDTO wareHouseDTO) {
        List<WareHouseEntity> wareHouseEntityList = wareHouseRepository.findAllByWarehouseCode(wareHouseDTO.getWarehouseCode());
        return wareHouseConverter.toWareHouseDTOList(wareHouseEntityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WareHouseDTO upsert(WareHouseDTO wareHouseDTO) {
        WareHouseEntity wareHouse;
        boolean existWareHouseCode = existCategoryAndDiscount(wareHouseDTO);
        if (wareHouseDTO.getId() != null){
            boolean exist = existWareHouse(wareHouseDTO);
            if (!exist){
                throw new ObjectNotFoundException(
                        404, "Cannot update warehouse id: "+wareHouseDTO.getId()
                );
            } else if (!existWareHouseCode) {
                throw new ObjectEmptyException(
                        406, "Category code or discount code is empty or not exist !"
                );
            }
            WareHouseEntity oldWareHouse = wareHouseRepository.findOneById(wareHouseDTO.getId());
            wareHouse = wareHouseConverter.toWareHouseEntity(wareHouseDTO, oldWareHouse);
        }else {
            List<WareHouseEntity> allWareHouseCode = wareHouseConverter.toWareHouseEntityList(getAllWareHouseCode(wareHouseDTO));
            if (!(allWareHouseCode).isEmpty()){
                throw new DuplicationCodeException(
                        400, "Warehouse code is duplicated !"
                );
            }else if (!existWareHouseCode) {
                throw new ObjectEmptyException(
                        406, "Category code or discount code is empty or not exist !"
                );
            }
            wareHouse = wareHouseConverter.toWareHouseEntity(wareHouseDTO);
        }
        DiscountTypeEntity discountType = discountTypeRepository.findOneByCode(wareHouseDTO.getDiscountTypeCode());
        CategoryEntity category = categoryRepository.findOneByCategoryCode(wareHouseDTO.getCategoryCode());
        wareHouse.setDiscountType(discountType);
        wareHouse.setCategory(category);
        return wareHouseConverter.toWareHouseDTO(wareHouseRepository.save(wareHouse));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteWareHouse(WareHouseDTO wareHouseDTO) {
        boolean exits = wareHouseRepository.existsById(wareHouseDTO.getId());
        if (!exits){
            throw new ObjectNotFoundException(
                    404, "Cannot delete id: "+wareHouseDTO.getId()
            );
        }
        this.wareHouseRepository.deleteById(wareHouseDTO.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existWareHouse(WareHouseDTO wareHouseDTO) {
        return wareHouseRepository.existsById(wareHouseDTO.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existCategoryAndDiscount(WareHouseDTO wareHouseDTO) {
        boolean existCategoryCode = categoryRepository.existsByCategoryCode(wareHouseDTO.getCategoryCode());
        boolean existDiscountCode = discountTypeRepository.existsByCode(wareHouseDTO.getDiscountTypeCode());
        return existDiscountCode && existCategoryCode;
    }
}
