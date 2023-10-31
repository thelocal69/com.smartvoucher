package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.converter.WareHouseConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.WareHouseDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.*;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.InputOutputException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.repository.*;
import com.smartvoucher.webEcommercesmartvoucher.service.IWareHouseService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class WareHouseService implements IWareHouseService {

    private final IWareHouseRepository wareHouseRepository;
    private final WareHouseConverter wareHouseConverter;
    private final IDiscountTypeRepository discountTypeRepository;
    private final ICategoryRepository categoryRepository;
    private final ILabelRepository labelRepository;
    private final Drive googleDrive;


    @Autowired
    public WareHouseService(final IWareHouseRepository wareHouseRepository,
                            final WareHouseConverter wareHouseConverter,
                            final IDiscountTypeRepository discountTypeRepository,
                            final ICategoryRepository categoryRepository,
                            final Drive googleDrive,
                            final ILabelRepository labelRepository
    ) {
        this.wareHouseRepository = wareHouseRepository;
        this.wareHouseConverter = wareHouseConverter;
        this.discountTypeRepository = discountTypeRepository;
        this.categoryRepository = categoryRepository;
        this.googleDrive = googleDrive;
        this.labelRepository = labelRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WareHouseDTO> getAllWareHouse() {
        List<WareHouseEntity> wareHouseEntityList = wareHouseRepository.findAll();
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
                        406, "Category code or discount code is empty or not exist !"
                );
            }
            WareHouseEntity oldWareHouse = wareHouseRepository.findOneById(wareHouseDTO.getId());
            wareHouse = wareHouseConverter.toWareHouseEntity(wareHouseDTO, oldWareHouse);
        } else {
            List<WareHouseEntity> allWareHouseCode = wareHouseConverter.toWareHouseEntityList(getAllWareHouseCode(wareHouseDTO));
            if (!(allWareHouseCode).isEmpty()) {
                throw new DuplicationCodeException(
                        400, "Warehouse code is duplicated !"
                );
            } else if (!existCategoryAndDiscountAndLabel(wareHouseDTO)) {
                throw new ObjectEmptyException(
                        406, "Category code or discount code is empty or not exist !"
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

    public Boolean isImageFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        assert fileExtension != null;
        return Arrays.asList("jpg", "png", "jpeg", "bmp").contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public File uploadWarehouseImages(MultipartFile fileName) {
        try {
            if (fileName.isEmpty()) {
                throw new InputOutputException(501, "Failed to store empty file", null);
            } else if (!isImageFile(fileName)) {
                throw new InputOutputException(500, "You can only upload image file", null);
            }
            float fileSizeInMegabytes = fileName.getSize() / 1_000_000.0f;
            if (fileSizeInMegabytes > 5.0f) {
                throw new InputOutputException(501, "File must be <= 5Mb", null);
            }
            File fileMetaData = new File();
            String folderId = "101aTGIyIgR4tIq88tT3lCE3_QWZcVP03";
            fileMetaData.setParents(Collections.singletonList(folderId));
            fileMetaData.setName(fileName.getOriginalFilename());
            return googleDrive.files().create(fileMetaData, new InputStreamContent(
                    fileName.getContentType(),
                    new ByteArrayInputStream(fileName.getBytes())
            )).setFields("id, webViewLink").execute();
        } catch (IOException ioException) {
            throw new InputOutputException(501, "Failed to store file", ioException);
        }
    }
}
