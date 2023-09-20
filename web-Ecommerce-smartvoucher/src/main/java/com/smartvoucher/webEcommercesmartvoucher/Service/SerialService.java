package com.smartvoucher.webEcommercesmartvoucher.Service;

import com.smartvoucher.webEcommercesmartvoucher.DTO.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.Entity.SerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.Repository.SerialRepository;
import com.smartvoucher.webEcommercesmartvoucher.Converter.ConverterEntitytoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerialService {

    private final SerialRepository serialRepository;

    @Autowired
    private ConverterEntitytoDTO converterEntitytoDTO;

    @Autowired
    public SerialService(SerialRepository serialRepository) {
        this.serialRepository = serialRepository;
    }

    public List<SerialDTO> findAllSerial() {

        List<SerialEntity> list = serialRepository.findAll();
        List<SerialDTO> listSerial = converterEntitytoDTO.findAllSerial(list);

        return listSerial;
    }
}
