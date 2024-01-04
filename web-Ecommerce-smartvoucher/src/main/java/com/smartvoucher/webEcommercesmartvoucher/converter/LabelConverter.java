package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.LabelDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.LabelEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LabelConverter {
    public LabelDTO toLabelDTO(LabelEntity labelEntity){
        LabelDTO labelDTO = new LabelDTO();
        labelDTO.setId(labelEntity.getId());
        labelDTO.setName(labelEntity.getName());
        labelDTO.setSlug(labelEntity.getSlug());
        return labelDTO;
    }

    public List<LabelDTO> toLabelDTOList(List<LabelEntity> labelEntityList){
        return labelEntityList.stream().map(this::toLabelDTO).collect(Collectors.toList());
    }

    public LabelEntity toLabelEntity(LabelDTO labelDTO){
        LabelEntity labelEntity = new LabelEntity();
        labelEntity.setId(labelDTO.getId());
        labelEntity.setName(labelDTO.getName());
        labelEntity.setSlug(labelDTO.getSlug());
        return labelEntity;
    }

    public List<LabelEntity> toLabelEntityList(List<LabelDTO> labelDTOList){
        return labelDTOList.stream().map(this::toLabelEntity).collect(Collectors.toList());
    }
}
