package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.LabelConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.LabelDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.LabelEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.repository.ILabelRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.ILabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        log.info("Get all label is completed !");
        return labelConverter.toLabelDTOList(labelEntityList);
    }

    @Override
    public ResponseOutput getAllLabel(int page, int limit, String sortBy, String sortField) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(
                Sort.Direction.fromString(sortBy), sortField)
        );
        List<LabelDTO> labelDTOList = labelConverter.toLabelDTOList(
                labelRepository.findAll(pageable).getContent()
        );
        if (labelDTOList.isEmpty()){
            log.info("List label is empty !");
            throw new ObjectNotFoundException(404, "List label is empty !");
        }
        int totalItem = (int) labelRepository.count();
        int totalPage = (int) Math.ceil((double) totalItem/limit);
        log.info("Get all label is completed !");
        return new ResponseOutput(
                page,
                totalItem,
                totalPage,
                labelDTOList
        );
    }

    @Override
    public List<String> getAllNameByLabel() {
        List<String> labelNameList = labelRepository.getAllByLabelName();
        if (labelNameList.isEmpty()){
            log.info("List label name is empty !");
            throw new ObjectEmptyException(404, "List label name is empty !");
        }
        log.info("Get list label name is successfully !");
        return labelNameList;
    }
}
