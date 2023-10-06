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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public List<ChainDTO> getAllChain() {
        List<ChainEntity> chainEntityList = chainRepository.findAll();
        return chainConverter.toChainDTOList(chainEntityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(readOnly = true)
    public List<ChainDTO> getAllChainCode(ChainDTO chainDTO) {
        List<ChainEntity> chainEntityList = chainRepository.findAllByChainCode(chainDTO.getChainCode());
        return chainConverter.toChainDTOList(chainEntityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteChain(ChainDTO chainDTO) {
        boolean exists = chainRepository.existsById(chainDTO.getId());
        if (exists){
            this.chainRepository.deleteById(chainDTO.getId());
            return  true;
        }else {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existChain(ChainDTO chainDTO) {
        return merchantRepository.existsById(chainDTO.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existMerchantCode(ChainDTO chainDTO) {
        return merchantRepository.existsByMerchantCode(chainDTO.getMerchantCode());
    }
}
