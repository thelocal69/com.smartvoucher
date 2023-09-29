package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.DiscountTypeDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;

import java.util.List;

public interface IDiscountTypeService {
    List<DiscountTypeDTO> getAllDiscount();
    List<DiscountTypeDTO> getAllDiscountTypeCode(DiscountTypeDTO discountTypeDTO);
    DiscountTypeDTO upsert(DiscountTypeDTO discountTypeDTO);
    Boolean deleteDiscountType(DiscountTypeDTO discountTypeDTO);
    Boolean existDiscount(DiscountTypeDTO discountTypeDTO);
}
