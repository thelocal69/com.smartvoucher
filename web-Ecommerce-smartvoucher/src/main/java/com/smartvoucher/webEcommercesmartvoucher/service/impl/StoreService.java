package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.StoreConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.StoreDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.ChainEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.MerchantEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.StoreEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
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
        List<StoreEntity> storeEntityList = storeRepository.findAllByStatus(1);
        if (storeEntityList.isEmpty()){
            throw new ObjectEmptyException(
                    404, "List store is empty !"
            );
        }
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
        boolean existStoreCode = existMerchantCodeAndChainCode(storeDTO);
        if (storeDTO.getId() != null){
            boolean exist = existStore(storeDTO);
            if (!exist){
                throw new ObjectNotFoundException(
                        404, "Cannot update store id: "+storeDTO.getId()
                );
            } else if (!existStoreCode) {
                throw new ObjectEmptyException(
                        406, "Merchant code or chain code is empty or not exist !"
                );
            }
            StoreEntity oldStore = storeRepository.findOneById(storeDTO.getId());
            store = storeConverter.toStoreEntity(storeDTO, oldStore);
        }else {
            List<StoreEntity> allStoreCode = storeConverter.toStoreEntityList(getAllStoreCode(storeDTO));
            if (!(allStoreCode).isEmpty()){
                throw new DuplicationCodeException(
                        400, "Store code is duplicated !"
                );
            }else if (!existStoreCode) {
                throw new ObjectEmptyException(
                        406, "Merchant code or chain code is empty or not exist !"
                );
            }
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
    public void deleteStore(StoreDTO storeDTO) {
        boolean exists = storeRepository.existsById(storeDTO.getId());
        if (!exists){
            throw new ObjectNotFoundException(
                    404, "Cannot delete store id: "+storeDTO.getId()
            );
        }
        this.storeRepository.deleteById(storeDTO.getId());
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
