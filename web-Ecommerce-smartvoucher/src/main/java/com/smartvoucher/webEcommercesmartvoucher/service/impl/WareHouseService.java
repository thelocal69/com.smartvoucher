package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.api.services.drive.model.File;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WareHouseService implements IWareHouseService {

    private final IWareHouseRepository wareHouseRepository;
    private final WareHouseConverter wareHouseConverter;
    private final IDiscountTypeRepository discountTypeRepository;
    private final ICategoryRepository categoryRepository;
    private final ILabelRepository labelRepository;
    private final UploadUtil uploadUtil;
    private final Gson gson;
    private final RedisTemplate<String ,String> redisTemplate;


    @Autowired
    public WareHouseService(final IWareHouseRepository wareHouseRepository,
                            final WareHouseConverter wareHouseConverter,
                            final IDiscountTypeRepository discountTypeRepository,
                            final ICategoryRepository categoryRepository,
                            final UploadUtil uploadUtil,
                            final ILabelRepository labelRepository,
                            final Gson gson,
                            final RedisTemplate<String, String> redisTemplate
    ) {
        this.wareHouseRepository = wareHouseRepository;
        this.wareHouseConverter = wareHouseConverter;
        this.discountTypeRepository = discountTypeRepository;
        this.categoryRepository = categoryRepository;
        this.uploadUtil = uploadUtil;
        this.labelRepository = labelRepository;
        this.gson = gson;
        this.redisTemplate = redisTemplate;
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
                log.info("Cannot update warehouse id: " + wareHouseDTO.getId());
                throw new ObjectNotFoundException(
                        404, "Cannot update warehouse id: " + wareHouseDTO.getId()
                );
            } else if (!existCategoryAndDiscountAndLabel(wareHouseDTO)) {
                log.info("Category code or discount code or label name is empty or not exist !");
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
            log.info("Update warehouse is completed !");
        } else {
            List<WareHouseEntity> allWareHouseCode = wareHouseConverter.toWareHouseEntityList(getAllWareHouseCode(wareHouseDTO));
            if (!(allWareHouseCode).isEmpty()) {
                log.info("Warehouse code is duplicated !");
                throw new DuplicationCodeException(
                        400, "Warehouse code is duplicated !"
                );
            }if (!existCategoryAndDiscountAndLabel(wareHouseDTO)) {
                log.info("Category code or discount code or label name is empty or not exist !");
                throw new ObjectEmptyException(
                        406, "Category code or discount code or label name is empty or not exist !"
                );
            }
            wareHouse = wareHouseConverter.toWareHouseEntity(wareHouseDTO);
            log.info("Insert warehouse is completed !");
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
        boolean existCategoryCode = categoryRepository.existsByCategoryCode(wareHouseDTO.getCategoryCode());
        boolean existDiscountCode = discountTypeRepository.existsByCode(wareHouseDTO.getDiscountTypeCode());
        boolean existLabelName = labelRepository.existsByName(wareHouseDTO.getLabelName());
        return existDiscountCode && existCategoryCode && existLabelName;
    }

    @Override
    public String uploadWarehouseImages(MultipartFile fileName) {
        String folderId = "101aTGIyIgR4tIq88tT3lCE3_QWZcVP03";
        File file = uploadUtil.uploadImages(fileName, folderId);
        return "https://drive.google.com/uc?export=view&id="+file.getId();
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
        List<WareHouseEntity> wareHouseEntityList;
        List<WareHouseDTO> wareHouseDTOList;
        if (Boolean.TRUE.equals(this.redisTemplate.hasKey("listWareHouseByLable"))){
            String data = redisTemplate.opsForValue().get("listWareHouseByLable");
            Type listData = new TypeToken<ArrayList<WareHouseDTO>>(){}.getType();
            wareHouseDTOList = gson.fromJson(data, listData);
        }else {
            wareHouseEntityList = wareHouseRepository.findAllByLabel(id);
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            wareHouseEntityList = wareHouseEntityList.stream()
                    .filter(warehouse -> warehouse.getAvailableTo() == null || currentTime.before(warehouse.getAvailableTo()))
                    .collect(Collectors.toList());

            if (wareHouseEntityList.isEmpty()) {
                log.info("List warehouse is empty !");
                throw new ObjectEmptyException(
                        404, "List warehouse is empty !"
                );
            }
            wareHouseDTOList = wareHouseConverter.toWareHouseDTOList(wareHouseEntityList);
            String data = gson.toJson(wareHouseDTOList);
            this.redisTemplate.opsForValue().set("listWareHouseByLable", data, 30, TimeUnit.MINUTES);
        }
        log.info("Get all warehouse by lable is completed !");
        return wareHouseDTOList;
        }
    @Override
    @Transactional(readOnly = true)
    public List<WareHouseDTO> getAllWarehouseByCategoryId(long id) {
        List<WareHouseEntity>wareHouseEntities;
        List<WareHouseDTO> wareHouseDTOList;
        if (Boolean.TRUE.equals(this.redisTemplate.hasKey("listWareHouseByCategoryId"))){
            String data = redisTemplate.opsForValue().get("listWareHouseByCategoryId");
            Type listType = new TypeToken<ArrayList<WareHouseDTO>>(){}.getType();
            wareHouseDTOList = gson.fromJson(data, listType);
        }else {
            wareHouseEntities = wareHouseRepository.findAllByCategoryId(id);
            if (wareHouseEntities.isEmpty()){
                log.info("List warehouse is empty !");
                throw new ObjectEmptyException(
                        404, "List warehouse is empty !"
                );
            }
            wareHouseDTOList = wareHouseConverter.toWareHouseDTOList(wareHouseEntities);
            String data = gson.toJson(wareHouseDTOList);
            this.redisTemplate.opsForValue().set("listWareHouseByCategoryId", data, 30, TimeUnit.MINUTES);
        }
        log.info("Get all warehouse by category code is completed !");
        return wareHouseDTOList;
    }


}
