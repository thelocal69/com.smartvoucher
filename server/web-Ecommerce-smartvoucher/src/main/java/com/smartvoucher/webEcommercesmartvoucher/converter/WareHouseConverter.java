package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.WareHouseDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.WarehouseNameDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WarehouseSerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.keys.WarehouseSerialKeys;
import com.smartvoucher.webEcommercesmartvoucher.repository.WarehouseSerialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WareHouseConverter {

    private final WarehouseSerialRepository warehouseSerialRepository;

    @Autowired
    public WareHouseConverter(WarehouseSerialRepository warehouseSerialRepository) {
        this.warehouseSerialRepository = warehouseSerialRepository;
    }

    public WareHouseDTO toWareHouseDTO(WareHouseEntity wareHouseEntity){
        return WareHouseDTO.builder()
                .id(wareHouseEntity.getId())
                .idCategory(wareHouseEntity.getCategory().getId())
                .idDiscountType(wareHouseEntity.getDiscountType().getId())
                .createdBy(wareHouseEntity.getCreatedBy())
                .createdAt(wareHouseEntity.getCreatedAt())
                .updatedBy(wareHouseEntity.getUpdateBy())
                .updatedAt(wareHouseEntity.getUpdateAt())
                .warehouseCode(wareHouseEntity.getWarehouseCode())
                .name(wareHouseEntity.getName())
                .price(wareHouseEntity.getPrice())
                .originalPrice(wareHouseEntity.getOriginalPrice())
                .description(wareHouseEntity.getDescription())
                .availableFrom(wareHouseEntity.getAvailableFrom())
                .availableTo(wareHouseEntity.getAvailableTo())
                .bannerUrl(wareHouseEntity.getBannerUrl())
                .capacity(wareHouseEntity.getCapacity())
                .categoryName(wareHouseEntity.getCategory().getName())
                .discountAmount(wareHouseEntity.getDiscountAmount())
                .discountTypeName(wareHouseEntity.getDiscountType().getName())
                .labelName(wareHouseEntity.getLabel().getName())
                .maxDiscountAmount(wareHouseEntity.getMaxDiscountAmount())
                .showOnWeb(wareHouseEntity.getShowOnWeb())
                .termOfUse(wareHouseEntity.getTermOfUse())
                .thumbnailUrl(wareHouseEntity.getThumbnailUrl())
                .voucherChannel(wareHouseEntity.getVoucherChannel())
                .status(wareHouseEntity.getStatus())
                .build();
    }

    public WarehouseNameDTO toWareHouseNameDTO(WareHouseEntity wareHouseEntity){
        WarehouseNameDTO warehouseNameDTO = new WarehouseNameDTO();
        warehouseNameDTO.setId(wareHouseEntity.getId());
        warehouseNameDTO.setKeyWord(wareHouseEntity.getName());
        return warehouseNameDTO;
    }

    public List<WarehouseNameDTO> toWareHouseNameDTOList(List<WareHouseEntity> wareHouseEntityList){
        return wareHouseEntityList.stream().map(this::toWareHouseNameDTO).collect(Collectors.toList());
    }

    public List<WareHouseDTO> toWareHouseDTOList(List<WareHouseEntity> wareHouseEntityList){
        return wareHouseEntityList.stream().map(this::toWareHouseDTO).collect(Collectors.toList());
    }

    public WareHouseEntity toWareHouseEntity(WareHouseDTO wareHouseDTO){
        return WareHouseEntity.builder()
                .id(wareHouseDTO.getId())
                .warehouseCode(wareHouseDTO.getWarehouseCode())
                .availableFrom(wareHouseDTO.getAvailableFrom())
                .availableTo(wareHouseDTO.getAvailableTo())
                .capacity(wareHouseDTO.getCapacity())
                .discountAmount(wareHouseDTO.getDiscountAmount())
                .bannerUrl(wareHouseDTO.getBannerUrl())
                .description(wareHouseDTO.getDescription())
                .maxDiscountAmount(wareHouseDTO.getMaxDiscountAmount())
                .name(wareHouseDTO.getName())
                .price(wareHouseDTO.getPrice())
                .originalPrice(wareHouseDTO.getOriginalPrice())
                .showOnWeb(wareHouseDTO.getShowOnWeb())
                .termOfUse(wareHouseDTO.getTermOfUse())
                .thumbnailUrl(wareHouseDTO.getThumbnailUrl())
                .voucherChannel(wareHouseDTO.getVoucherChannel())
                .status(wareHouseDTO.getStatus())
                .build();
    }

    public WareHouseEntity toWareHouseEntity(WareHouseDTO wareHouseDTO, WareHouseEntity wareHouseEntity){
        wareHouseEntity.setId(wareHouseDTO.getId());
        wareHouseEntity.setThumbnailUrl(wareHouseDTO.getThumbnailUrl());
        wareHouseEntity.setBannerUrl(wareHouseDTO.getBannerUrl());
        wareHouseEntity.setDiscountAmount(wareHouseDTO.getDiscountAmount());
        wareHouseEntity.setMaxDiscountAmount(wareHouseDTO.getMaxDiscountAmount());
        wareHouseEntity.setName(wareHouseDTO.getName());
        wareHouseEntity.setPrice(wareHouseDTO.getPrice());
        wareHouseEntity.setOriginalPrice(wareHouseDTO.getOriginalPrice());
        wareHouseEntity.setTermOfUse(wareHouseDTO.getTermOfUse());
        wareHouseEntity.setDescription(wareHouseDTO.getDescription());
        wareHouseEntity.setStatus(wareHouseDTO.getStatus());
        wareHouseEntity.setAvailableFrom(wareHouseDTO.getAvailableFrom());
        wareHouseEntity.setAvailableTo(wareHouseDTO.getAvailableTo());
        wareHouseEntity.setShowOnWeb(wareHouseDTO.getShowOnWeb());
        wareHouseEntity.setVoucherChannel(wareHouseDTO.getVoucherChannel());
        wareHouseEntity.setCapacity(wareHouseDTO.getCapacity());
        return wareHouseEntity;
    }

    public List<WareHouseEntity> toWareHouseEntityList(List<WareHouseDTO> wareHouseDTOList){
        return wareHouseDTOList.stream().map(this::toWareHouseEntity).collect(Collectors.toList());
    }

    public void saveWarehouseSerial(SerialEntity serialEntity, WareHouseEntity wareHouseEntity) {
        WarehouseSerialKeys keys = new WarehouseSerialKeys();
        keys.setIdSerial(serialEntity.getId());
        keys.setIdWarehouse(wareHouseEntity.getId());
        WarehouseSerialEntity warehouseSerialEntity = new WarehouseSerialEntity();
        warehouseSerialEntity.setKeys(keys);
        warehouseSerialRepository.save(warehouseSerialEntity);
    }
}
