package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.LabelConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.LabelDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.LabelEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.repository.ILabelRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.ILabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class LabelService implements ILabelService {

    private final LabelConverter labelConverter;
    private final ILabelRepository labelRepository;

    @Autowired
    public LabelService(final LabelConverter labelConverter,
                        final ILabelRepository labelRepository) {
        this.labelConverter = labelConverter;
        this. labelRepository = labelRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LabelDTO> getAllLabel() {
        List<LabelEntity> labelEntityList = labelRepository.findAll();
        if (labelEntityList.isEmpty()){
            log.info("List label is empty !");
            throw new ObjectNotFoundException(404, "List label is empty !");
        }
        log.info("Get all lable is completed !");
        return labelConverter.toLabelDTOList(labelEntityList);
    }
}
