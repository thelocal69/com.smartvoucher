package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.ChainConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.ChainDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.ChainEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.MerchantEntity;
import com.smartvoucher.webEcommercesmartvoucher.repository.IChainRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.IMerchantRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChainService implements IChainService {
    private final IChainRepository chainRepository;
    private final IMerchantRepository merchantRepository;
    private final ChainConverter chainConverter;

    @Autowired
    public ChainService(final IChainRepository chainRepository, final ChainConverter chainConverter,
    final IMerchantRepository merchantRepository){
        this.chainRepository = chainRepository;
        this.chainConverter = chainConverter;
        this.merchantRepository = merchantRepository;
    }

    @Override
    public List<ChainDTO> getAllChain() {
        List<ChainEntity> chainEntityList = chainRepository.findAll();
        return chainConverter.toChainDTOList(chainEntityList);
    }

    @Override
    public ChainDTO upsert(ChainDTO chainDTO) {
        ChainEntity chainEntity;
        if (chainDTO.getId() != null){
            ChainEntity oldChainEntity = chainRepository.findOneById(chainDTO.getId());
            chainEntity = chainConverter.toChainEntity(chainDTO, oldChainEntity);
        }else {
            chainEntity = chainConverter.toChainEntity(chainDTO);
        }
        MerchantEntity merchant = merchantRepository.findOneByMerchantCode(chainDTO.getMerchantCode());
        chainEntity.setMerchant(merchant);
        return chainConverter.toChainDTO(chainRepository.save(chainEntity));
    }

    @Override
    public List<ChainDTO> getAllChainCode(ChainDTO chainDTO) {
        List<ChainEntity> chainEntityList = chainRepository.findAllByChainCode(chainDTO.getChainCode());
        return chainConverter.toChainDTOList(chainEntityList);
    }

}
