package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.ChainDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;

import java.util.List;

public interface IChainService {
    List<ChainDTO> getAllChain();
    ChainDTO upsert(ChainDTO chainDTO);
    List<ChainDTO> getAllChainCode(ChainDTO chainDTO);
    //ChainDTO findOneById(ChainDTO chainDTO);
}
