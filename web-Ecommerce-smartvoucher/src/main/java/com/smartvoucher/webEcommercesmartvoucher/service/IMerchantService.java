package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;

import java.util.List;

public interface IMerchantService {
    List<MerchantDTO> getAllMerchant();
    List<MerchantDTO> getAllMerchantCode(MerchantDTO merchantDTO);
    MerchantDTO getMerchantCode(MerchantDTO merchantDTO);
    MerchantDTO upsertMerchant(MerchantDTO merchantDTO);
    Boolean deleteMerchant(MerchantDTO merchantDTO);
    Boolean existMerchant(MerchantDTO merchantDTO);
}
