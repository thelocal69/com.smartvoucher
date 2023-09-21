package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;

import java.util.List;

public interface IMerchantService {
    List<MerchantDTO> getAllMerchant();
    MerchantDTO insertMerchant(MerchantDTO merchantDTO);
    List<MerchantDTO> getAllMerchantCode(MerchantDTO merchantDTO);
    MerchantDTO updateMerchant(MerchantDTO merchantDTO, Long id);
    Boolean deleteMerchant(Long id);
}
