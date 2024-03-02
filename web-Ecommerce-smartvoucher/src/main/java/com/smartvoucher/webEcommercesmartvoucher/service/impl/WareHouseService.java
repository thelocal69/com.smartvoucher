package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.converter.WareHouseConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.WareHouseDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.WarehouseNameDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.CategoryEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.DiscountTypeEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.LabelEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.CheckCapacityException;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.repository.ICategoryRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.IDiscountTypeRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.ILabelRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.IWareHouseRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IWareHouseService;
import com.smartvoucher.webEcommercesmartvoucher.util.UploadGoogleDriveUtil;
import com.smartvoucher.webEcommercesmartvoucher.util.UploadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
public class WareHouseService implements IWareHouseService {

    private final IWareHouseRepository wareHouseRepository;
    private final WareHouseConverter wareHouseConverter;
    private final IDiscountTypeRepository discountTypeRepository;
    private final ICategoryRepository categoryRepository;
    private final ILabelRepository labelRepository;
    private final UploadGoogleDriveUtil uploadGoogleDriveUtil;
    private final UploadLocalUtil uploadLocalUtil;
    @Value("${drive_view}")
    private String driveUrl;
    @Value("${domain_file}")
    private String domainFile;


    @Autowired
    public WareHouseService(final IWareHouseRepository wareHouseRepository,
                            final WareHouseConverter wareHouseConverter,
                            final IDiscountTypeRepository discountTypeRepository,
                            final ICategoryRepository categoryRepository,
                            final UploadGoogleDriveUtil uploadGoogleDriveUtil,
                            final ILabelRepository labelRepository,
                            final UploadLocalUtil uploadLocalUtil
    ) {
        this.wareHouseRepository = wareHouseRepository;
        this.wareHouseConverter = wareHouseConverter;
        this.discountTypeRepository = discountTypeRepository;
        this.categoryRepository = categoryRepository;
        this.uploadGoogleDriveUtil = uploadGoogleDriveUtil;
        this.labelRepository = labelRepository;
        this.uploadLocalUtil = uploadLocalUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WareHouseDTO> getAllWareHouse() {
        List<WareHouseDTO> wareHouseDTOList = wareHouseConverter.toWareHouseDTOList(
                wareHouseRepository.findAll()
        );
        if (wareHouseDTOList.isEmpty()){
            log.info("List warehouse is empty !");
            throw new ObjectEmptyException(500, "List warehouse is empty !");
        }
        log.info("Get all warehouse is completed !");
        return wareHouseDTOList;
    }

    @Override
    public ResponseOutput getAllWareHouse(int page, int limit, String sortBy, String sortField) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.fromString(sortBy), sortField));
        List<WareHouseDTO> wareHouseDTOList = wareHouseConverter.toWareHouseDTOList(
                wareHouseRepository.findAll(pageable).getContent()
        );
        if (wareHouseDTOList.isEmpty()){
            log.info("Warehouse list is empty !");
            throw new ObjectEmptyException(406, "Warehouse list is empty !");
        }
        int totalItem = (int) wareHouseRepository.count();
        int totalPage = (int) Math.ceil((double) totalItem / limit);
        return new ResponseOutput(
                page,
                totalItem,
                totalPage,
                wareHouseDTOList
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<WareHouseDTO> getAllWareHouseCode(WareHouseDTO wareHouseDTO) {
        List<WareHouseEntity> wareHouseEntityList = wareHouseRepository.findAllByWarehouseCode(wareHouseDTO.getWarehouseCode());
        return wareHouseConverter.toWareHouseDTOList(wareHouseEntityList);
    }

    @Override
    public List<WareHouseDTO> searchByWarehouseName(String name) {
        return wareHouseConverter.toWareHouseDTOList(
                wareHouseRepository.searchAllByNameContainingIgnoreCase(name)
        );
    }

    @Override
    public List<WarehouseNameDTO> searchAllWarehouseName(String name) {
        return wareHouseConverter.toWareHouseNameDTOList(
                wareHouseRepository.searchAllByNameContainingIgnoreCase(name)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WareHouseDTO upsert(WareHouseDTO wareHouseDTO) {
        WareHouseEntity wareHouse;
        if (wareHouseDTO.getId() != null) {
            if (!existWareHouse(wareHouseDTO)) {
                log.info("Cannot update warehouse id: " + wareHouseDTO.getId());
                throw new ObjectNotFoundException(
                        404, "Cannot update warehouse id: " + wareHouseDTO.getId()
                );
            } else if (!existCategoryAndDiscountAndLabel(wareHouseDTO)) {
                log.info("Category name or discount name or label name is empty or not exist !");
                throw new ObjectEmptyException(
                        406, "Category name or discount name or label name is empty or not exist !"
                );
            }
            WareHouseEntity oldWareHouse = wareHouseRepository.findOneById(wareHouseDTO.getId());
            if (wareHouseDTO.getCapacity() < oldWareHouse.getCapacity()){
                throw new CheckCapacityException(500, "Not allow decrease capacity, only increase !");
            }
            wareHouse = wareHouseConverter.toWareHouseEntity(wareHouseDTO, oldWareHouse);
            log.info("Update warehouse is completed !");
        } else {
            List<WareHouseEntity> allWareHouseCode = wareHouseConverter.toWareHouseEntityList(getAllWareHouseCode(wareHouseDTO));
            if (!(allWareHouseCode).isEmpty()) {
                log.info("Warehouse code is duplicated !");
                throw new DuplicationCodeException(
                        400, "Warehouse code is duplicated !"
                );
            }if (!existCategoryAndDiscountAndLabel(wareHouseDTO)) {
                log.info("Category name or discount name or label name is empty or not exist !");
                throw new ObjectEmptyException(
                        406, "Category name or discount name or label name is empty or not exist !"
                );
            }
            wareHouse = wareHouseConverter.toWareHouseEntity(wareHouseDTO);
            log.info("Insert warehouse is completed !");
        }
        DiscountTypeEntity discountType = discountTypeRepository.findOneByName(wareHouseDTO.getDiscountTypeName());
        CategoryEntity category = categoryRepository.findOneByName(wareHouseDTO.getCategoryName());
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
            log.info("Cannot delete id: " + wareHouseDTO.getId());
            throw new ObjectNotFoundException(
                    404, "Cannot delete id: " + wareHouseDTO.getId()
            );
        }
        log.info("Delete warehouse is completed !");
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
        boolean existCategoryName = categoryRepository.existsByName(wareHouseDTO.getCategoryName());
        boolean existDiscountName = discountTypeRepository.existsByName(wareHouseDTO.getDiscountTypeName());
        boolean existLabelName = labelRepository.existsByName(wareHouseDTO.getLabelName());
        return existCategoryName && existDiscountName && existLabelName;
    }

    @Override
    public String uploadWarehouseImages(MultipartFile fileName) {
        String folderId = "101aTGIyIgR4tIq88tT3lCE3_QWZcVP03";
        File file = uploadGoogleDriveUtil.uploadImages(fileName, folderId);
        return driveUrl+file.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public WareHouseDTO getWarehouseById(Long id) {
        WareHouseEntity wareHouseEntity = wareHouseRepository.findOneById(id);
        if (wareHouseEntity == null) {
            log.info("Warehouse by id "+ id + " is not found !");
            throw new ObjectNotFoundException(
                    404, "Warehouse not found !"
            );
        }
        log.info("Get warehouse by id " + id + " is completed !");
        return wareHouseConverter.toWareHouseDTO(wareHouseEntity);
    }
    @Override
    @Transactional(readOnly = true)
    public List<WareHouseDTO> getAllWarehousesByLabel(int id) {
        List<WareHouseDTO> wareHouseDTOList = wareHouseConverter.toWareHouseDTOList(
                wareHouseRepository.findAllByLabel(id)
        );
        if (wareHouseDTOList.isEmpty()){
            log.info("List warehouse label is empty !");
            throw new ObjectEmptyException(500, "List warehouse bt label is empty !");
        }
        log.info("Get all warehouse by lable is completed !");
        return wareHouseDTOList;
        }

    @Override
    @Transactional(readOnly = true)
    public ResponseOutput getAllWarehousesByLabel(String slug, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        List<WareHouseDTO> wareHouseDTOList = wareHouseConverter.toWareHouseDTOList(
                wareHouseRepository.findAllByLabelSlug(slug, pageable)
        );
        if (wareHouseDTOList.isEmpty()){
            log.info("List warehouse label is empty !");
            throw new ObjectEmptyException(500, "List warehouse by label is empty !");
        }
        int totalItem = wareHouseRepository.countByLabel(slug);
        int totalPage = (int) Math.ceil((double) totalItem / limit);
        return new ResponseOutput(
                page,
                totalItem,
                totalPage,
                wareHouseDTOList
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseOutput getAllWarehouseByCategoryName(String name, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        List<WareHouseDTO> wareHouseDTOList = wareHouseConverter.toWareHouseDTOList(
                wareHouseRepository.findAllByCategoryName(name, pageable)
        );
        if (wareHouseDTOList.isEmpty()){
            log.info("List warehouse category is empty !");
            throw new ObjectEmptyException(500, "List warehouse bt category is empty !");
        }
        int totalItem = wareHouseRepository.countByCategory(name);
        int totalPage = (int) Math.ceil((double) totalItem / limit);
        return new ResponseOutput(
                page,
                totalItem,
                totalPage,
                wareHouseDTOList
        );
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadLocalWarehouseImages(MultipartFile fileName) {
        String folderName = "warehouse";
        String imageName = uploadLocalUtil.storeFile(fileName, folderName);
        return domainFile+"/warehouse/"+imageName;
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] readImageUrl(String fileName) {
        String folderName = "warehouse";
        return uploadLocalUtil.readFileContent(fileName, folderName);
    }


}
