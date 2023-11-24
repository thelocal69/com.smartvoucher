package com.smartvoucher.webEcommercesmartvoucher.service;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMerchantService {
    List<MerchantDTO> getAllMerchant();
    List<MerchantDTO> getAllMerchantCode(MerchantDTO merchantDTO);
    MerchantDTO getMerchantCode(MerchantDTO merchantDTO);
    MerchantDTO upsertMerchant(MerchantDTO merchantDTO);
    void deleteMerchant(MerchantDTO merchantDTO);
    Boolean existMerchant(MerchantDTO merchantDTO);
    String uploadMerchantImages(MultipartFile fileName);
}
