package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.MerchantConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.MerchantEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.repository.IMerchantRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MerchantService implements IMerchantService {

    private final IMerchantRepository merchantRepository;
    private final MerchantConverter merchantConverter;

    @Autowired
    public MerchantService(final  IMerchantRepository merchantRepository, final MerchantConverter merchantConverter){
        this.merchantRepository = merchantRepository;
        this.merchantConverter = merchantConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerchantDTO> getAllMerchant() {
        List<MerchantEntity> merchantEntityList = merchantRepository.findAll();
        if (merchantEntityList.isEmpty()){
            throw new ObjectNotFoundException(
                    404, "List merchant is empty !"
            );
        }
        return merchantConverter.toMerchantDTOList(merchantEntityList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerchantDTO> getAllMerchantCode(MerchantDTO merchantDTO) {
        List<MerchantEntity> merchantEntityList = merchantRepository.findAllByMerchantCode(
                merchantDTO.getMerchantCode().trim());
        return merchantConverter.toMerchantDTOList(merchantEntityList);
    }

    @Override
    @Transactional(readOnly = true)
    public MerchantDTO getMerchantCode(MerchantDTO merchantDTO) {
        MerchantEntity merchant = merchantRepository.findOneByMerchantCode(merchantDTO.getMerchantCode());
        return merchantConverter.toMerchantDTO(merchant);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MerchantDTO upsertMerchant(MerchantDTO merchantDTO) {
        MerchantEntity merchant;
        if (merchantDTO.getId() != null){
            boolean exist = existMerchant(merchantDTO);
            if (!exist){
                throw new ObjectNotFoundException(
                        404, "Cannot update chain id: "+merchantDTO.getId()
                );
            }
            MerchantEntity oldMerchant = merchantRepository.findOneById(merchantDTO.getId());
            merchant = merchantConverter.toMerchantEntity(merchantDTO, oldMerchant);
        }else{
            List<MerchantEntity> merchantEntityList = merchantConverter.toMerchantEntityList(getAllMerchantCode(merchantDTO));
            if (!merchantEntityList.isEmpty()){
                throw new DuplicationCodeException(
                        400, "Merchant code is duplicated !"
                );
            }
            merchant = merchantConverter.toMerchantEntity(merchantDTO);
        }
        return merchantConverter.toMerchantDTO(merchantRepository.save(merchant));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMerchant(MerchantDTO merchantDTO) {
        boolean exist = merchantRepository.existsById(merchantDTO.getId());
        if (!exist){
            throw new ObjectNotFoundException(
                    404, "Cannot delete id: "+merchantDTO.getId()
            );
        }
        this.merchantRepository.deleteById(merchantDTO.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existMerchant(MerchantDTO merchantDTO) {
        return merchantRepository.existsById(merchantDTO.getId());
    }
}
