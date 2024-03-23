package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.StoreDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;

import java.util.List;

public interface IStoreService {
    List<StoreDTO> getAllStore();
    List<StoreDTO> getAllStoreCode(StoreDTO storeDTO);
    ResponseOutput getAllStoreCode(int page, int limit, String sortBy, String sortField);
    List<StoreDTO> searchAllStoreByName(String name);
    StoreDTO upsert(StoreDTO storeDTO);
    void deleteStore(StoreDTO storeDTO);
    Boolean existStore(StoreDTO storeDTO);
    Boolean existMerchantNameAndChainName(StoreDTO storeDTO);
}
