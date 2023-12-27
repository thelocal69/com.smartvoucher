package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.DiscountTypeDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;

import java.util.List;

public interface IDiscountTypeService {
    List<DiscountTypeDTO> getAllDiscount();
    List<DiscountTypeDTO> getAllDiscountTypeCode(DiscountTypeDTO discountTypeDTO);
    List<DiscountTypeDTO> searchByName(String name);
    List<String> getAllNameByDiscount();
    ResponseOutput getAllDiscountType(int page, int limit, String sortBy, String sortField);
    DiscountTypeDTO upsert(DiscountTypeDTO discountTypeDTO);
    void deleteDiscountType(DiscountTypeDTO discountTypeDTO);
    Boolean existDiscount(DiscountTypeDTO discountTypeDTO);
}
