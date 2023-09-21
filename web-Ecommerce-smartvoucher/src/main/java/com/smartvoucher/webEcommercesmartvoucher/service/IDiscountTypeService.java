package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.DiscountTypeDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;

import java.util.List;

public interface IDiscountTypeService {
    List<DiscountTypeDTO> getAllDiscount();
}
