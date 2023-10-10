package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.StoreConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.StoreDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.ChainEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.MerchantEntity;
import com.smartvoucher.webEcommercesmartvoucher.repository.IChainRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.IMerchantRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.IStoreRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoreService implements IStoreService {

    private final IStoreRepository storeRepository;
    private final StoreConverter storeConverter;
    private final IChainRepository chainRepository;
    private final IMerchantRepository merchantRepository;

    @Autowired
    public StoreService(final IStoreRepository storeRepository,
                        final StoreConverter storeConverter,
                        final IChainRepository chainRepository,
                        final IMerchantRepository merchantRepository
    ) {
        this.storeRepository = storeRepository;
        this.storeConverter = storeConverter;
        this.chainRepository = chainRepository;
        this.merchantRepository = merchantRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreDTO> getAllStore() {
        List<StoreEntity> storeEntityList = storeRepository.findAll();
        return storeConverter.toStoreDTOList(storeEntityList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreDTO> getAllStoreCode(StoreDTO storeDTO) {
        List<StoreEntity> storeEntityList = storeRepository.findAllByStoreCode(storeDTO.getStoreCode());
        return storeConverter.toStoreDTOList(storeEntityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreDTO upsert(StoreDTO storeDTO) {
        StoreEntity store;
        if (storeDTO.getId() != null){
            StoreEntity oldStore = storeRepository.findOneById(storeDTO.getId());
            store = storeConverter.toStoreEntity(storeDTO, oldStore);
        }else {
            store = storeConverter.toStoreEntity(storeDTO);
        }
        ChainEntity chain = chainRepository.findOneByChainCode(storeDTO.getChainCode());
        MerchantEntity merchant = merchantRepository.findOneByMerchantCode(storeDTO.getMerchantCode());
        store.setChain(chain);
        store.setMerchant(merchant);
        return storeConverter.toStoreDTO(storeRepository.save(store));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteStore(StoreDTO storeDTO) {
        boolean exists = storeRepository.existsById(storeDTO.getId());
        if (exists){
            this.storeRepository.deleteById(storeDTO.getId());
            return true;
        }else {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existStore(StoreDTO storeDTO) {
        return storeRepository.existsById(storeDTO.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existMerchantCodeAndChainCode(StoreDTO storeDTO) {
        boolean existMerchantCode = merchantRepository.existsByMerchantCode(storeDTO.getMerchantCode());
        boolean existChainCode = chainRepository.existsByChainCode(storeDTO.getChainCode());
        return existMerchantCode && existChainCode;
    }
}
