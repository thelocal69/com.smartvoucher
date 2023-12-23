package com.smartvoucher.webEcommercesmartvoucher.service;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.dto.ChainDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IChainService {
    List<ChainDTO> getAllChain();
    ResponseOutput getAllChain(int page, int limit, String sortBy, String sortField);
    ChainDTO upsert(ChainDTO chainDTO);
    List<ChainDTO> getAllChainCode(ChainDTO chainDTO);
    void deleteChain(ChainDTO chainDTO);
    Boolean existChain(ChainDTO chainDTO);
    Boolean existMerchantCode(ChainDTO chainDTO);
    String uploadChainImages(MultipartFile fileName);
}
