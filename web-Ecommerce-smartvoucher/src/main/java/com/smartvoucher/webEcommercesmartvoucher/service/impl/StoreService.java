package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.StoreConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.StoreDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.*;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.repository.*;
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
    private final WarehouseSerialRepository warehouseSerialRepository;
    private final SerialRepository serialRepository;
    private final TicketRepository ticketRepository;
    private final TicketHistoryRepository ticketHistoryRepository;
    private final WarehouseStoreRepository warehouseStoreRepository;

    @Autowired
    public StoreService(final IStoreRepository storeRepository,
                        final StoreConverter storeConverter,
                        final IChainRepository chainRepository,
                        final IMerchantRepository merchantRepository,
                        final WarehouseSerialRepository warehouseSerialRepository,
                        final SerialRepository serialRepository,
                        final TicketRepository ticketRepository,
                        final TicketHistoryRepository ticketHistoryRepository,
                        final WarehouseStoreRepository warehouseStoreRepository
    ) {
        this.storeRepository = storeRepository;
        this.storeConverter = storeConverter;
        this.chainRepository = chainRepository;
        this.merchantRepository = merchantRepository;
        this.warehouseSerialRepository = warehouseSerialRepository;
        this.serialRepository = serialRepository;
        this.ticketRepository = ticketRepository;
        this.ticketHistoryRepository = ticketHistoryRepository;
        this.warehouseStoreRepository = warehouseStoreRepository;
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
    public void deleteStore(long id) {
        StoreEntity store = storeRepository.findOneById(id);
        if (store == null){
            throw new ObjectNotFoundException(
                    404, "Cannot delete store id: "+id
            );
        }
        List<TicketEntity> listTicketEntity = ticketRepository.findByIdStore(store);
        for(TicketEntity ticketEntity : listTicketEntity){
            ticketHistoryRepository.deleteByIdTicket(ticketEntity);
            ticketRepository.delete(ticketEntity);
            warehouseSerialRepository.deleteByIdSerial(ticketEntity.getIdSerial());
            serialRepository.deleteById(ticketEntity.getIdSerial().getId());
        }
        warehouseStoreRepository.deleteByIdStore(store);
        this.storeRepository.deleteById(id);
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
