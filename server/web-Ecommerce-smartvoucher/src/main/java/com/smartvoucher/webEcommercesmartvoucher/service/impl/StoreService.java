package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.StoreConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.StoreDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.*;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.repository.*;
import com.smartvoucher.webEcommercesmartvoucher.service.IStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
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
            log.info("List store is empty !");
            throw new ObjectEmptyException(
                    404, "List store is empty !"
            );
        }
        log.info("Get all store completed !");
        return storeConverter.toStoreDTOList(storeEntityList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreDTO> getAllStoreCode(StoreDTO storeDTO) {
        List<StoreEntity> storeEntityList = storeRepository.findAllByStoreCode(storeDTO.getStoreCode());
        return storeConverter.toStoreDTOList(storeEntityList);
    }

    @Override
    public ResponseOutput getAllStoreCode(int page, int limit, String sortBy, String sortField) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.fromString(sortBy), sortField));
        List<StoreDTO> storeDTOList = storeConverter.toStoreDTOList(
                storeRepository.findAll(pageable).getContent()
        );
        if (storeDTOList.isEmpty()){
            log.info("Store is empty !");
            throw new ObjectEmptyException(404, "Store is empty !");
        }
        int totalItem = (int) storeRepository.count();
        int totalPage = (int) Math.ceil((double) totalItem / limit);
        return new ResponseOutput(
                page,
                totalItem,
                totalPage,
                storeDTOList
        );
    }

    @Override
    public List<StoreDTO> searchAllStoreByName(String name) {
        return storeConverter.toStoreDTOList(
                storeRepository.searchAllByNameContainingIgnoreCase(name)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreDTO upsert(StoreDTO storeDTO) {
        StoreEntity store;
        boolean existStoreName = existMerchantNameAndChainName(storeDTO);
        if (storeDTO.getId() != null){
            boolean exist = existStore(storeDTO);
            if (!exist){
                log.info("Cannot update store id: "+storeDTO.getId());
                throw new ObjectNotFoundException(
                        404, "Cannot update store id: "+storeDTO.getId()
                );
            } else if (!existStoreName) {
                log.info("Merchant name or chain name is empty or not exist !");
                throw new ObjectEmptyException(
                        406, "Merchant name or chain name is empty or not exist !"
                );
            }
            StoreEntity oldStore = storeRepository.findOneById(storeDTO.getId());
            store = storeConverter.toStoreEntity(storeDTO, oldStore);
            log.info("Update store is completed !");
        }else {
            List<StoreEntity> allStoreCode = storeConverter.toStoreEntityList(getAllStoreCode(storeDTO));
            if (!(allStoreCode).isEmpty()){
                log.info("Store code is duplicated !");
                throw new DuplicationCodeException(
                        400, "Store code is duplicated !"
                );
            }else if (!existStoreName) {
                log.info("Merchant name or chain name is empty or not exist !");
                throw new ObjectEmptyException(
                        406, "Merchant name or chain name is empty or not exist !"
                );
            }
            store = storeConverter.toStoreEntity(storeDTO);
            log.info("Insert store is completed !");
        }
        ChainEntity chain = chainRepository.findOneByName(storeDTO.getChainName());
        MerchantEntity merchant = merchantRepository.findOnByName(storeDTO.getMerchantName());
        store.setChain(chain);
        store.setMerchant(merchant);
        return storeConverter.toStoreDTO(storeRepository.save(store));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteStore(StoreDTO storeDTO) {
        boolean exists = storeRepository.existsById(storeDTO.getId());
        if (!exists){
            log.info("Cannot delete store id: "+storeDTO.getId());
            throw new ObjectNotFoundException(
                    404, "Cannot delete store id: "+storeDTO.getId()
            );
        }
        this.storeRepository.deleteById(storeDTO.getId());
        log.info("Delete store is completed !");
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existStore(StoreDTO storeDTO) {
        return storeRepository.existsById(storeDTO.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existMerchantNameAndChainName(StoreDTO storeDTO) {
        boolean existMerchantName = merchantRepository.existsByName(storeDTO.getMerchantName());
        boolean existChainName = chainRepository.existsByName(storeDTO.getChainName());
        return existMerchantName && existChainName;
    }
}
