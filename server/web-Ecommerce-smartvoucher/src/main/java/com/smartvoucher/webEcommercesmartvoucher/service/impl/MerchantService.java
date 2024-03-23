package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.converter.MerchantConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.MerchantDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.MerchantEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.repository.IMerchantRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IMerchantService;
import com.smartvoucher.webEcommercesmartvoucher.util.UploadGoogleDriveUtil;
import com.smartvoucher.webEcommercesmartvoucher.util.UploadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
public class MerchantService implements IMerchantService {

    private final IMerchantRepository merchantRepository;
    private final MerchantConverter merchantConverter;
    private final UploadGoogleDriveUtil uploadGoogleDriveUtil;
    private final UploadLocalUtil uploadLocalUtil;
    @Value("${drive_view}")
    private String driveUrl;
    @Value("${domain_file}")
    private String domainUrl;

    @Autowired
    public MerchantService(final  IMerchantRepository merchantRepository,
                           final MerchantConverter merchantConverter,
                           final UploadGoogleDriveUtil uploadGoogleDriveUtil,
                           final UploadLocalUtil uploadLocalUtil){
        this.merchantRepository = merchantRepository;
        this.merchantConverter = merchantConverter;
        this.uploadGoogleDriveUtil = uploadGoogleDriveUtil;
        this.uploadLocalUtil = uploadLocalUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerchantDTO> getAllMerchant() {
        List<MerchantEntity> merchantEntityList = merchantRepository.findAllByStatus(1);
        if (merchantEntityList.isEmpty()){
            log.info("List merchant is empty !");
            throw new ObjectNotFoundException(
                    404, "List merchant is empty !"
            );
        }
        log.info("Get all merchant is completed !");
        return merchantConverter.toMerchantDTOList(merchantEntityList);
    }

    @Override
    public List<String> getALlMerchantName() {
        List<String> getAllName = merchantRepository.getAllMerchantName();
        if (getAllName.isEmpty()){
            log.info("List merchant name is empty !");
            throw new ObjectNotFoundException(
                    404, "List merchant name is empty !"
            );
        }
        log.info("Get all merchant name is completed !");
        return getAllName;
    }

    @Override
    public ResponseOutput getAllMerchant(int page, int limit, String sortBy, String sortField) {
        Pageable pageable = PageRequest.of(
                page - 1,
                limit,
                Sort.by(Sort.Direction.fromString(sortBy), sortField)
                );
        List<MerchantDTO> merchantDTOList = merchantConverter.toMerchantDTOList(
                merchantRepository.findAll(pageable).getContent()
        );
        if (merchantDTOList.isEmpty()){
            log.info("List merchant is empty !");
            throw new ObjectNotFoundException(
                    404, "List merchant is empty !"
            );
        }
        int totalItem = (int) merchantRepository.count();
        int totalPage = (int) Math.ceil((double) totalItem / limit);
        log.info("Get all merchant is completed !");
        return new ResponseOutput(
                page,
                totalItem,
                totalPage,
                merchantDTOList
        );
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
                log.info("Cannot update merchant id: "+merchantDTO.getId());
                throw new ObjectNotFoundException(
                        404, "Cannot update merchant id: "+merchantDTO.getId()
                );
            }
            MerchantEntity oldMerchant = merchantRepository.findOneById(merchantDTO.getId());
            merchant = merchantConverter.toMerchantEntity(merchantDTO, oldMerchant);
            log.info("Update merchant is completed !");
        }else{
            List<MerchantEntity> merchantEntityList = merchantConverter.toMerchantEntityList(getAllMerchantCode(merchantDTO));
            if (!merchantEntityList.isEmpty()){
                log.info("Merchant code is duplicated !");
                throw new DuplicationCodeException(
                        400, "Merchant code is duplicated !"
                );
            }
            merchant = merchantConverter.toMerchantEntity(merchantDTO);
            log.info("Insert merchant is completed !");
        }
        return merchantConverter.toMerchantDTO(merchantRepository.save(merchant));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMerchant(MerchantDTO merchantDTO) {
        boolean exist = merchantRepository.existsById(merchantDTO.getId());
        if (!exist){
            log.info("Cannot delete id: "+merchantDTO.getId());
            throw new ObjectNotFoundException(
                    404, "Cannot delete id: "+merchantDTO.getId()
            );
        }
        log.info("Delete merchant is completed !");
        this.merchantRepository.deleteById(merchantDTO.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existMerchant(MerchantDTO merchantDTO) {
        return merchantRepository.existsById(merchantDTO.getId());
    }

    @Override
    public String uploadMerchantImages(MultipartFile fileName) {
        String folderId = "1z6B_EyGuGN5AJX8tqrGnZkT6XMiKuTg5";
        File file = uploadGoogleDriveUtil.uploadImages(fileName, folderId);
        return driveUrl+file.getId();
    }

    @Override
    public String uploadLocalMerchantImages(MultipartFile fileName) {
        String folderName = "merchant";
        String imageName = uploadLocalUtil.storeFile(fileName, folderName);
        return domainUrl+"/merchant/"+imageName;
    }

    @Override
    public byte[] readImageUrl(String fileName) {
        String folderName = "merchant";
        return uploadLocalUtil.readFileContent(fileName, folderName);
    }

    @Override
    public List<MerchantDTO> searchMerchantByName(String name) {
        return merchantConverter.toMerchantDTOList(
                merchantRepository.searchMerchantEntityByNameContainingIgnoreCase(name)
        );
    }
}
