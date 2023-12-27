package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.DiscountTypeConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.DiscountTypeDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.DiscountTypeEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.repository.IDiscountTypeRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IDiscountTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
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
        List<DiscountTypeEntity> discountTypeEntityList = discountTypeRepository.findAllByStatus(1);
        if (discountTypeEntityList.isEmpty()){
            log.info("List category is empty !");
            throw new ObjectEmptyException(
                    404, "List category is empty !"
            );
        }
        log.info("Get all discount type is completed !");
        return discountTypeConverter.toDiscountTypeDTOList(discountTypeEntityList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscountTypeDTO> getAllDiscountTypeCode(DiscountTypeDTO discountTypeDTO) {
        List<DiscountTypeEntity> discountTypeEntityList = discountTypeRepository.findByCode(discountTypeDTO.getCode());
        return discountTypeConverter.toDiscountTypeDTOList(discountTypeEntityList);
    }

    @Override
    public List<DiscountTypeDTO> searchByName(String name) {
        return discountTypeConverter.toDiscountTypeDTOList(
                discountTypeRepository.searchAllByNameContainingIgnoreCase(name)
        );
    }

    @Override
    public List<String> getAllNameByDiscount() {
        List<String> discountNameList = discountTypeRepository.getAllByDiscountName();
        if (discountNameList.isEmpty()){
            log.info("List discount name is empty !");
            throw new ObjectEmptyException(404, "List discount name is empty !");
        }
        log.info("Get list discount name is successfully !");
        return discountNameList;
    }

    @Override
    public ResponseOutput getAllDiscountType(int page, int limit, String sortBy, String sortField) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.fromString(sortBy), sortField));
        List<DiscountTypeDTO> discountTypeDTOList = discountTypeConverter.toDiscountTypeDTOList(
                discountTypeRepository.findAll(pageable).getContent()
        );
        if (discountTypeDTOList.isEmpty()){
            log.info("List category is empty !");
            throw new ObjectEmptyException(
                    404, "List category is empty !"
            );
        }
        int totalItem = (int) discountTypeRepository.count();
        int totalPage = (int) Math.ceil((double) totalItem / limit);
        log.info("Get all discount type is completed !");
        return new ResponseOutput(
                page,
                totalItem,
                totalPage,
                discountTypeDTOList
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DiscountTypeDTO upsert(DiscountTypeDTO discountTypeDTO) {
        DiscountTypeEntity discountType;
        if (discountTypeDTO.getId() != null){
            boolean exist = existDiscount(discountTypeDTO);
            if (!exist){
                log.info("Cannot update discount id: "+discountTypeDTO.getId());
                throw new ObjectNotFoundException(
                        404, "Cannot update discount id: "+discountTypeDTO.getId()
                );
            }
            DiscountTypeEntity oldDiscountType = discountTypeRepository.findOneById(discountTypeDTO.getId());
            discountType = discountTypeConverter.toDiscountTypeEntity(discountTypeDTO, oldDiscountType);
            log.info("Update discount type is completed !");
        }else {
            List<DiscountTypeEntity> discountTypeEntityList = discountTypeConverter.toDiscountTypeEntityList(getAllDiscountTypeCode(discountTypeDTO));
            if (!discountTypeEntityList.isEmpty()){
                log.info("Discount type code is duplicated !");
                throw new DuplicationCodeException(
                        400, "Discount type code is duplicated !"
                );
            }
            discountType = discountTypeConverter.toDiscountTypeEntity(discountTypeDTO);
            log.info("Insert discount type is completed !");
        }
        return discountTypeConverter.toDiscountTypeDTO(discountTypeRepository.save(discountType));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDiscountType(DiscountTypeDTO discountTypeDTO) {
        boolean exists = discountTypeRepository.existsById(discountTypeDTO.getId());
        if (!exists){
            log.info("Cannot delete id: "+discountTypeDTO.getId());
            throw new ObjectNotFoundException(
                    404, "Cannot delete id: "+discountTypeDTO.getId()
            );
        }
        this.discountTypeRepository.deleteById(discountTypeDTO.getId());
        log.info("Delete discount type is completed ! ");
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existDiscount(DiscountTypeDTO discountTypeDTO) {
        return discountTypeRepository.existsById(discountTypeDTO.getId());
    }
}
