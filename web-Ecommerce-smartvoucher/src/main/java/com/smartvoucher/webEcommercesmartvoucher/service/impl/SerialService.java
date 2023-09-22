package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.SerialConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.repository.ISerialRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.ISerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerialService implements ISerialService {

    private final ISerialRepository serialRepository;
    private final SerialConverter serialConverter;

    @Autowired
    public SerialService(final ISerialRepository serialRepository, final SerialConverter serialConverter) {
        this.serialRepository = serialRepository;
        this.serialConverter = serialConverter;
    }

    @Override
    public List<SerialDTO> getAllSerial() {
        List<SerialEntity> serialEntityList = serialRepository.findAll();
        return serialConverter.toSerialDTOList(serialEntityList);
    }
}
