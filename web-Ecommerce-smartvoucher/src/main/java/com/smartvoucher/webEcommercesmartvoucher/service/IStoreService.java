package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.StoreDTO;

import java.util.List;

public interface IStoreService {
    List<StoreDTO> getAllStore();
    List<StoreDTO> getAllStoreCode(StoreDTO storeDTO);
    StoreDTO upsert(StoreDTO storeDTO);
    void deleteStore(long id);
    Boolean existStore(StoreDTO storeDTO);
    Boolean existMerchantCodeAndChainCode(StoreDTO storeDTO);
}
