package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.converter.MerchantConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.MerchantEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.InputOutputException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.repository.IMerchantRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IMerchantService;
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
import java.util.UUID;

@Service
public class MerchantService implements IMerchantService {

    private final IMerchantRepository merchantRepository;
    private final MerchantConverter merchantConverter;
    private final Drive googleDrive;

    @Autowired
    public MerchantService(final  IMerchantRepository merchantRepository,
                           final MerchantConverter merchantConverter,
                           final Drive googleDrive){
        this.merchantRepository = merchantRepository;
        this.merchantConverter = merchantConverter;
        this.googleDrive = googleDrive;
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
                        404, "Cannot update merchant id: "+merchantDTO.getId()
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

    public Boolean isImageFile(MultipartFile file){
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        assert fileExtension != null;
        return Arrays.asList("jpg", "png", "jpeg", "bmp").contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public File uploadMerchantImages(MultipartFile fileName) {
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
            String folderId = "1z6B_EyGuGN5AJX8tqrGnZkT6XMiKuTg5";
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
