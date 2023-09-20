package com.smartvoucher.webEcommercesmartvoucher.Service;

import com.smartvoucher.webEcommercesmartvoucher.Entity.SerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.Repository.SerialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerialService {

    private final SerialRepository serialRepository;

    @Autowired
    public SerialService(SerialRepository serialRepository) {
        this.serialRepository = serialRepository;
    }

    public List<SerialEntity> findAllSerial() {

        return serialRepository.findAll();
    }
}
