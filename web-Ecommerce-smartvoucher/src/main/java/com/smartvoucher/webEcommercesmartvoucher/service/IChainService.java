package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.ChainDTO;

import java.util.List;

public interface IChainService {
    List<ChainDTO> getAllChain();
    ChainDTO upsert(ChainDTO chainDTO);
    List<ChainDTO> getAllChainCode(ChainDTO chainDTO);
    void deleteChain(ChainDTO chainDTO);
    Boolean existChain(ChainDTO chainDTO);
    Boolean existMerchantCode(ChainDTO chainDTO);
}
