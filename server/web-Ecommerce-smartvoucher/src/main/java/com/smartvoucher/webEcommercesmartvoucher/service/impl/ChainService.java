package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.converter.ChainConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.ChainDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.ChainEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.MerchantEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.repository.IChainRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.IMerchantRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IChainService;
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
public class ChainService implements IChainService {
    private final IChainRepository chainRepository;
    private final IMerchantRepository merchantRepository;
    private final ChainConverter chainConverter;
    private final UploadGoogleDriveUtil uploadGoogleDriveUtil;
    private final UploadLocalUtil uploadLocalUtil;
    @Value("${drive_view}")
    private String driveUrl;
    @Value("${domain_file}")
    private String domainFile;

    @Autowired
    public ChainService(final IChainRepository chainRepository,
                        final ChainConverter chainConverter,
                        final IMerchantRepository merchantRepository,
                        final UploadGoogleDriveUtil uploadGoogleDriveUtil,
                        final UploadLocalUtil uploadLocalUtil){
        this.chainRepository = chainRepository;
        this.chainConverter = chainConverter;
        this.merchantRepository = merchantRepository;
        this.uploadGoogleDriveUtil = uploadGoogleDriveUtil;
        this.uploadLocalUtil = uploadLocalUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChainDTO> getAllChain() {
        List<ChainEntity> chainEntityList = chainRepository.findAllByStatus(1);
        if (chainEntityList.isEmpty()){
            log.info("List chain is empty !");
            throw new ObjectEmptyException(
                    404, "List chain is empty !"
            );
        }
        log.info("Get All chain success !");
        return chainConverter.toChainDTOList(chainEntityList);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseOutput getAllChain(int page, int limit, String sortBy, String sortField) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.fromString(sortBy), sortField));
        List<ChainDTO> chainDTOList = chainConverter.toChainDTOList(
                chainRepository.findAll(pageable).getContent()
        );
        if (chainDTOList.isEmpty()){
            log.info("List chain is empty !");
            throw new ObjectEmptyException(
                    404, "List chain is empty !"
            );
        }
        int totalItem = (int) chainRepository.count();
        int totalPage = (int) Math.ceil((double) totalItem / limit);
        log.info("Get all chain is completed !");
        return new ResponseOutput(
                page,
                totalItem,
                totalPage,
                chainDTOList
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChainDTO upsert(ChainDTO chainDTO) {
        boolean existMerchantName = existMerchantName(chainDTO);
        ChainEntity chainEntity;
        if (chainDTO.getId() != null){
            boolean exist = existChain(chainDTO);
            if (!exist){
                log.info("Cannot update chain id: "+chainDTO.getId());
                throw new ObjectNotFoundException(
                        404, "Cannot update chain id: "+chainDTO.getId()
                );
            } else if (!existMerchantName) {
                log.info("Merchant name is empty or not exist !");
                throw new ObjectEmptyException(
                        406, "Merchant name is empty or not exist !"
                );
            }
            ChainEntity oldChainEntity = chainRepository.findOneById(chainDTO.getId());
            chainEntity = chainConverter.toChainEntity(chainDTO, oldChainEntity);
            log.info("Update chain is completed !");
        }else {
            List<ChainEntity> allChainCode = chainConverter.toChainEntityList(getAllChainCode(chainDTO));
            if (!(allChainCode).isEmpty()){
                log.info("Chain code is duplicated !");
                throw new DuplicationCodeException(
                        400, "Chain code is duplicated !"
                );
            }else if (!existMerchantName) {
                log.info("Merchant name is empty or not exist !");
                throw new ObjectEmptyException(
                        406, "Merchant name is empty or not exist !"
                );
            }
            chainEntity = chainConverter.toChainEntity(chainDTO);
            log.info("Insert chain is completed !");
        }
        MerchantEntity merchant = merchantRepository.findOnByName(chainDTO.getMerchantName());
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
    @Transactional(readOnly = true)
    public List<ChainDTO> searchAllChainName(String name) {
        return chainConverter.toChainDTOList(
                chainRepository.searchAllByNameContainingIgnoreCase(name)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllChainName() {
        List<String> getAllName = chainRepository.getChainName();
        if (getAllName.isEmpty()){
            log.info("List chain name is empty !");
            throw new ObjectEmptyException(404, "List chain name is empty !");
        }
        log.info("Get all name is successfully !");
        return getAllName;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteChain(ChainDTO chainDTO) {
        boolean exists = chainRepository.existsById(chainDTO.getId());
        if (!exists){
            log.info("Cannot delete id: "+chainDTO.getId());
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
    public Boolean existMerchantName(ChainDTO chainDTO) {
        return merchantRepository.existsByName(chainDTO.getMerchantName());
    }

    @Override
    public String uploadChainImages(MultipartFile fileName) {
        String folderId = "1u73jDfQwDXvzlmKSVLb5CAI6DNPvylRH";
        File file = uploadGoogleDriveUtil.uploadImages(fileName, folderId);
       return driveUrl+file.getId();
    }

    @Override
    public String uploadLocalChainImages(MultipartFile fileName) {
        String folderName = "chain";
        String imageName = uploadLocalUtil.storeFile(fileName, folderName);
        return domainFile+"/chain/"+imageName;
    }

    @Override
    public byte[] readImageUrl(String fileName) {
        String folderName = "chain";
        return uploadLocalUtil.readFileContent(fileName, folderName);
    }
}
