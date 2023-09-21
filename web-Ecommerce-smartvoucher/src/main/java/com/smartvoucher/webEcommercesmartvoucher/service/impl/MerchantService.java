package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.MerchantConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.MerchantEntity;
import com.smartvoucher.webEcommercesmartvoucher.repository.IMerchantRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IMerchantService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    public List<MerchantDTO> getAllMerchant() {
        List<MerchantEntity> merchantEntityList = merchantRepository.findAll();
        return merchantConverter.toMerchantDTOList(merchantEntityList);
    }

    @Override
    public MerchantDTO insertMerchant(MerchantDTO merchantDTO) {
        MerchantEntity merchantEntity = merchantRepository.save(merchantConverter.toMerchantEntity(merchantDTO));
        return merchantConverter.toMerchantDTO(merchantEntity);
    }

    @Override
    public List<MerchantDTO> getAllMerchantCode(MerchantDTO merchantDTO) {
        List<MerchantEntity> merchantEntityList = merchantRepository.findAllByMerchantCode(
                merchantDTO.getMerchantCode().trim());
        return merchantConverter.toMerchantDTOList(merchantEntityList);
    }

    @Override
    public MerchantDTO updateMerchant(MerchantDTO merchantDTO, Long id) {
        MerchantEntity merchantEntity = merchantRepository.findById(id).map(
                merchantEntity1 -> {
                    merchantEntity1.setMerchantCode(merchantDTO.getMerchantCode());
                    merchantEntity1.setName(merchantDTO.getName());
                    merchantEntity1.setLegalName(merchantDTO.getLegalName());
                    merchantEntity1.setLogoUrl(merchantDTO.getLogoUrl());
                    merchantEntity1.setEmail(merchantDTO.getEmail());
                    merchantEntity1.setAddress(merchantDTO.getAddress());
                    merchantEntity1.setPhone(merchantDTO.getPhone());
                    merchantEntity1.setDescription(merchantDTO.getDescription());
                    merchantEntity1.setStatus(merchantEntity1.getStatus());
                    return merchantRepository.save(merchantEntity1);
                }
        ).orElseGet(
                () -> {
                    merchantDTO.setId(id);
                    return merchantRepository.save(merchantConverter.toMerchantEntity(merchantDTO));
                }
        );
        return  merchantConverter.toMerchantDTO(merchantEntity);
    }

    @Override
    public Boolean deleteMerchant(Long id) {
        boolean exist = merchantRepository.existsById(id);
        if (exist){
            this.merchantRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
