package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.converter.ChainConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.ChainDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.ChainEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.MerchantEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.InputOutputException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.repository.IChainRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.IMerchantRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IChainService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ChainService implements IChainService {
    private final IChainRepository chainRepository;
    private final IMerchantRepository merchantRepository;
    private final ChainConverter chainConverter;
    private final Drive googleDrive;

    @Autowired
    public ChainService(final IChainRepository chainRepository,
                        final ChainConverter chainConverter,
                        final IMerchantRepository merchantRepository,
                        final Drive googleDrive){
        this.chainRepository = chainRepository;
        this.chainConverter = chainConverter;
        this.merchantRepository = merchantRepository;
        this.googleDrive = googleDrive;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChainDTO> getAllChain() {
        List<ChainEntity> chainEntityList = chainRepository.findAll();
        if (chainEntityList.isEmpty()){
            throw new ObjectEmptyException(
                    404, "List chain is empty !"
            );
        }
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
                throw new ObjectNotFoundException(
                        404, "Cannot update chain id: "+chainDTO.getId()
                );
            } else if (!existMerchantCode) {
                throw new ObjectEmptyException(
                        406, "Merchant code is empty or not exist !"
                );
            }
            ChainEntity oldChainEntity = chainRepository.findOneById(chainDTO.getId());
            chainEntity = chainConverter.toChainEntity(chainDTO, oldChainEntity);
        }else {
            List<ChainEntity> allChainCode = chainConverter.toChainEntityList(getAllChainCode(chainDTO));
            if (!(allChainCode).isEmpty()){
                throw new DuplicationCodeException(
                        400, "Chain code is duplicated !"
                );
            }else if (!existMerchantCode) {
                throw new ObjectEmptyException(
                        406, "Merchant code is empty or not exist !"
                );
            }
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
    public void deleteChain(ChainDTO chainDTO) {
        boolean exists = chainRepository.existsById(chainDTO.getId());
        if (!exists){
            throw new ObjectNotFoundException(
                    404, "Cannot delete id: "+chainDTO.getId()
            );
        }
        this.chainRepository.deleteById(chainDTO.getId());
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

    public Boolean isImageFile(MultipartFile file){
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        assert fileExtension != null;
        return Arrays.asList("jpg", "png", "jpeg", "bmp").contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public File uploadChainImages(MultipartFile fileName) {
        try {
            if (fileName.isEmpty()){
                throw new InputOutputException(501, "Failed to store empty file", null);
            } else if (!isImageFile(fileName)) {
                throw new InputOutputException(500, "You can only upload image file", null);
            }
            float fileSizeInMegabytes = fileName.getSize() / 1_000_000.0f;
            if (fileSizeInMegabytes > 5.0f) {
                throw new InputOutputException(501, "File must be <= 5Mb", null);
            }
            File fileMetaData = new File();
            String folderId = "1u73jDfQwDXvzlmKSVLb5CAI6DNPvylRH";
            fileMetaData.setParents(Collections.singletonList(folderId));
            fileMetaData.setName(fileName.getOriginalFilename());
            return googleDrive.files().create(fileMetaData, new InputStreamContent(
                    fileName.getContentType(),
                    new ByteArrayInputStream(fileName.getBytes())
            )).setFields("id, webViewLink").execute();
        }catch (IOException ioException) {
            throw new InputOutputException(501, "Failed to store file", ioException);
        }
    }
    }
