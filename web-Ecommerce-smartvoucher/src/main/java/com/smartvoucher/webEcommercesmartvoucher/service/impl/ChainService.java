package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.converter.ChainConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.ChainDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.ChainEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.MerchantEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.repository.IChainRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.IMerchantRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IChainService;
import com.smartvoucher.webEcommercesmartvoucher.util.UploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
public class ChainService implements IChainService {
    private final IChainRepository chainRepository;
    private final IMerchantRepository merchantRepository;
    private final ChainConverter chainConverter;
    private final UploadUtil uploadUtil;

    @Autowired
    public ChainService(final IChainRepository chainRepository,
                        final ChainConverter chainConverter,
                        final IMerchantRepository merchantRepository,
                        final UploadUtil uploadUtil){
        this.chainRepository = chainRepository;
        this.chainConverter = chainConverter;
        this.merchantRepository = merchantRepository;
        this.uploadUtil = uploadUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChainDTO> getAllChain() {
        List<ChainEntity> chainEntityList = chainRepository.findAllByStatus(1);
        if (chainEntityList.isEmpty()){
            log.warn("List chain is empty !");
            throw new ObjectEmptyException(
                    404, "List chain is empty !"
            );
        }
        log.info("Get All chain success !");
        return chainConverter.toChainDTOList(chainEntityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChainDTO upsert(ChainDTO chainDTO) {
        boolean existMerchantCode = existMerchantCode(chainDTO);
        ChainEntity chainEntity;
        if (chainDTO.getId() != null){
            boolean exist = existChain(chainDTO);
            if (!exist){
                log.warn("Cannot update chain id: "+chainDTO.getId());
                throw new ObjectNotFoundException(
                        404, "Cannot update chain id: "+chainDTO.getId()
                );
            } else if (!existMerchantCode) {
                log.warn("Merchant code is empty or not exist !");
                throw new ObjectEmptyException(
                        406, "Merchant code is empty or not exist !"
                );
            }
            ChainEntity oldChainEntity = chainRepository.findOneById(chainDTO.getId());
            chainEntity = chainConverter.toChainEntity(chainDTO, oldChainEntity);
            log.info("Update chain is completed !");
        }else {
            List<ChainEntity> allChainCode = chainConverter.toChainEntityList(getAllChainCode(chainDTO));
            if (!(allChainCode).isEmpty()){
                log.warn("Chain code is duplicated !");
                throw new DuplicationCodeException(
                        400, "Chain code is duplicated !"
                );
            }else if (!existMerchantCode) {
                log.warn("Merchant code is empty or not exist !");
                throw new ObjectEmptyException(
                        406, "Merchant code is empty or not exist !"
                );
            }
            chainEntity = chainConverter.toChainEntity(chainDTO);
            log.info("Insert chain is completed !");
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
    public void deleteChain(ChainDTO chainDTO) {
        boolean exists = chainRepository.existsById(chainDTO.getId());
        if (!exists){
            log.warn("Cannot delete id: "+chainDTO.getId());
            throw new ObjectNotFoundException(
                    404, "Cannot delete id: "+chainDTO.getId()
            );
        }
        this.chainRepository.deleteById(chainDTO.getId());
        log.info("Delete chain is completed !");
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existChain(ChainDTO chainDTO) {
        return chainRepository.existsById(chainDTO.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existMerchantCode(ChainDTO chainDTO) {
        return merchantRepository.existsByMerchantCode(chainDTO.getMerchantCode());
    }

    @Override
    public File uploadChainImages(MultipartFile fileName) {
        String folderId = "1u73jDfQwDXvzlmKSVLb5CAI6DNPvylRH";
       return uploadUtil.uploadImages(fileName, folderId);
    }
    }
