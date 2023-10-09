package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.DiscountTypeConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.DiscountTypeDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.DiscountTypeEntity;
import com.smartvoucher.webEcommercesmartvoucher.repository.IDiscountTypeRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IDiscountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiscountTypeService implements IDiscountTypeService {

    private final IDiscountTypeRepository discountTypeRepository;
    private final DiscountTypeConverter discountTypeConverter;

    @Autowired
    public DiscountTypeService(final IDiscountTypeRepository discountTypeRepository, final DiscountTypeConverter discountTypeConverter) {
        this.discountTypeRepository = discountTypeRepository;
        this.discountTypeConverter = discountTypeConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscountTypeDTO> getAllDiscount() {
        List<DiscountTypeEntity> discountTypeEntityList = discountTypeRepository.findAll();
        return discountTypeConverter.toDiscountTypeDTOList(discountTypeEntityList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscountTypeDTO> getAllDiscountTypeCode(DiscountTypeDTO discountTypeDTO) {
        List<DiscountTypeEntity> discountTypeEntityList = discountTypeRepository.findByCode(discountTypeDTO.getCode());
        return discountTypeConverter.toDiscountTypeDTOList(discountTypeEntityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DiscountTypeDTO upsert(DiscountTypeDTO discountTypeDTO) {
        DiscountTypeEntity discountType;
        if (discountTypeDTO.getId() != null){
            DiscountTypeEntity oldDiscountType = discountTypeRepository.findOneById(discountTypeDTO.getId());
            discountType = discountTypeConverter.toDiscountTypeEntity(discountTypeDTO, oldDiscountType);
        }else {
            discountType = discountTypeConverter.toDiscountTypeEntity(discountTypeDTO);
        }
        return discountTypeConverter.toDiscountTypeDTO(discountTypeRepository.save(discountType));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteDiscountType(DiscountTypeDTO discountTypeDTO) {
        boolean exists = discountTypeRepository.existsById(discountTypeDTO.getId());
        if (exists){
            this.discountTypeRepository.deleteById(discountTypeDTO.getId());
            return true;
        }else {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existDiscount(DiscountTypeDTO discountTypeDTO) {
        return discountTypeRepository.existsById(discountTypeDTO.getId());
    }
}
