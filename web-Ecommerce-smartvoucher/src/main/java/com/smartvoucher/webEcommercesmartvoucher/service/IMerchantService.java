package com.smartvoucher.webEcommercesmartvoucher.service;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMerchantService {
    List<MerchantDTO> getAllMerchant();

    ResponseOutput getAllMerchant(int page, int limit, String sortBy, String sortField);
    List<MerchantDTO> getAllMerchantCode(MerchantDTO merchantDTO);
    MerchantDTO getMerchantCode(MerchantDTO merchantDTO);
    MerchantDTO upsertMerchant(MerchantDTO merchantDTO);
    void deleteMerchant(MerchantDTO merchantDTO);
    Boolean existMerchant(MerchantDTO merchantDTO);
    String uploadMerchantImages(MultipartFile fileName);
}
