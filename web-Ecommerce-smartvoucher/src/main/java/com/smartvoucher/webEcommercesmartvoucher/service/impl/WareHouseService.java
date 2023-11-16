package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.converter.WareHouseConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.WareHouseDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.CategoryEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.DiscountTypeEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.LabelEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.CheckCapacityException;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.repository.ICategoryRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.IDiscountTypeRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.ILabelRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.IWareHouseRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IWareHouseService;
import com.smartvoucher.webEcommercesmartvoucher.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class WareHouseService implements IWareHouseService {

    private final IWareHouseRepository wareHouseRepository;
    private final WareHouseConverter wareHouseConverter;
    private final IDiscountTypeRepository discountTypeRepository;
    private final ICategoryRepository categoryRepository;
    private final ILabelRepository labelRepository;
    private final UploadUtil uploadUtil;


    @Autowired
    public WareHouseService(final IWareHouseRepository wareHouseRepository,
                            final WareHouseConverter wareHouseConverter,
                            final IDiscountTypeRepository discountTypeRepository,
                            final ICategoryRepository categoryRepository,
                            final UploadUtil uploadUtil,
                            final ILabelRepository labelRepository
    ) {
        this.wareHouseRepository = wareHouseRepository;
        this.wareHouseConverter = wareHouseConverter;
        this.discountTypeRepository = discountTypeRepository;
        this.categoryRepository = categoryRepository;
        this.uploadUtil = uploadUtil;
        this.labelRepository = labelRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WareHouseDTO> getAllWareHouse() {
        List<WareHouseEntity> wareHouseEntityList = wareHouseRepository.findAllByStatus(1);
        if (wareHouseEntityList.isEmpty()) {
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
        if (wareHouseDTO.getId() != null) {
            if (!existWareHouse(wareHouseDTO)) {
                throw new ObjectNotFoundException(
                        404, "Cannot update warehouse id: " + wareHouseDTO.getId()
                );
            } else if (!existCategoryAndDiscountAndLabel(wareHouseDTO)) {
                throw new ObjectEmptyException(
                        406, "Category code or discount code or label name is empty or not exist !"
                );
            }
            WareHouseEntity oldWareHouse = wareHouseRepository.findOneById(wareHouseDTO.getId());
            wareHouseDTO.setDiscountTypeCode(oldWareHouse.getDiscountType().getCode());
            if (wareHouseDTO.getCapacity() < oldWareHouse.getCapacity()){
                throw new CheckCapacityException(500, "Not allow decrease capacity, only increase !");
            }
            wareHouse = wareHouseConverter.toWareHouseEntity(wareHouseDTO, oldWareHouse);
        } else {
            List<WareHouseEntity> allWareHouseCode = wareHouseConverter.toWareHouseEntityList(getAllWareHouseCode(wareHouseDTO));
            if (!(allWareHouseCode).isEmpty()) {
                throw new DuplicationCodeException(
                        400, "Warehouse code is duplicated !"
                );
            } else if (!existCategoryAndDiscountAndLabel(wareHouseDTO)) {
                throw new ObjectEmptyException(
                        406, "Category code or discount code or label name is empty or not exist !"
                );
            }
            wareHouse = wareHouseConverter.toWareHouseEntity(wareHouseDTO);
        }
        DiscountTypeEntity discountType = discountTypeRepository.findOneByCode(wareHouseDTO.getDiscountTypeCode());
        CategoryEntity category = categoryRepository.findOneByCategoryCode(wareHouseDTO.getCategoryCode());
        LabelEntity label = labelRepository.findOneByName(wareHouseDTO.getLabelName());
        wareHouse.setDiscountType(discountType);
        wareHouse.setCategory(category);
        wareHouse.setLabel(label);
        return wareHouseConverter.toWareHouseDTO(wareHouseRepository.save(wareHouse));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteWareHouse(WareHouseDTO wareHouseDTO) {
        boolean exits = wareHouseRepository.existsById(wareHouseDTO.getId());
        if (!exits) {
            throw new ObjectNotFoundException(
                    404, "Cannot delete id: " + wareHouseDTO.getId()
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
    public Boolean existCategoryAndDiscountAndLabel(WareHouseDTO wareHouseDTO) {
        boolean existCategoryCode = categoryRepository.existsByCategoryCode(wareHouseDTO.getCategoryCode());
        boolean existDiscountCode = discountTypeRepository.existsByCode(wareHouseDTO.getDiscountTypeCode());
        boolean existLabelName = labelRepository.existsByName(wareHouseDTO.getLabelName());
        return existDiscountCode && existCategoryCode && existLabelName;
    }

    @Override
    public File uploadWarehouseImages(MultipartFile fileName) {
        String folderId = "101aTGIyIgR4tIq88tT3lCE3_QWZcVP03";
        return uploadUtil.uploadImages(fileName, folderId);
    }

    @Override
    @Transactional(readOnly = true)
    public WareHouseDTO getWarehouseById(Long id) {
        WareHouseEntity wareHouseEntity = wareHouseRepository.findOneById(id);
        if (wareHouseEntity == null) {
            throw new ObjectNotFoundException(
                    404, "Warehouse not found !"
            );
        }
        return wareHouseConverter.toWareHouseDTO(wareHouseEntity);
    }
    @Override
    @Transactional(readOnly = true)
    public List<WareHouseDTO> getAllWarehousesByLabel(int id) {
        List<WareHouseEntity> wareHouseEntityList = wareHouseRepository.findAllByLabel(id);
        return wareHouseConverter.toWareHouseDTOList(wareHouseEntityList);
    }
}
