package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.SerialConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.SerialRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.ISerialService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class SerialService implements ISerialService {

    private final SerialRepository serialRepository;

    private SerialConverter serialConverter;

    @Autowired
    public SerialService(SerialRepository serialRepository,
                         SerialConverter serialConverter) {
        this.serialRepository = serialRepository;
        this.serialConverter = serialConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseObject getAllSerial() {

        ResponseObject responseObject = new ResponseObject();
        responseObject.setStatusCode(200);
        responseObject.setMessage("List Serial");

        try {

            List<SerialEntity> list = serialRepository.findAll();
            List<SerialDTO> listSerial = serialConverter.findAllSerial(list);

        responseObject.setData(listSerial);

        } catch (Exception e) {
            System.out.println("Serial Service : " + e.getLocalizedMessage());
            return new ResponseObject(500, e.getLocalizedMessage(), "Not found List Serial !");
        }

        return responseObject;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseObject insertSerial(@NonNull SerialDTO serialDTO) {

        boolean isSuccess = false;
        int status = 501; // 501 : Not Implemented

        SerialEntity checkSerialCode = serialRepository.findSerialBySerialCode(serialDTO.getSerialCode());

            if(checkSerialCode == null){
                try {
                // save information Serial
                serialRepository.save(serialConverter.insertSerial(serialDTO));
                isSuccess = true;
                status = 200;

                } catch (javax.validation.ConstraintViolationException ex) {
                    // Validation Error
                    throw new javax.validation.ConstraintViolationException("Validation Fail!", ex.getConstraintViolations());

                } catch (Exception e) {
                    // error not specific
                    System.out.println("Serial Service : " + e.getLocalizedMessage() );
                    return new ResponseObject(500 , e.getLocalizedMessage() ,isSuccess);
                }
            }

        String message = (isSuccess == true) ? "Add serial success!":"Serial Code is available!";

        return new ResponseObject(status, message, isSuccess);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseObject updateSerial(@NonNull SerialDTO serialDTO) {

        boolean isSuccess = false;
        int status = 501;  // 501 : not implemented

        // orElse(null) : return Object / if Object not found, will return null
        SerialEntity oldSerial = serialRepository.findById(serialDTO.getId()).orElse(null);
        SerialEntity checkSerial = serialRepository.findSerialBySerialCode(serialDTO.getSerialCode());

        if (checkSerial == null) {
            if (oldSerial != null){
                try {

                serialRepository.save(serialConverter.updateSerial(serialDTO, oldSerial));
                isSuccess = true;
                status = 200;

                } catch (javax.validation.ConstraintViolationException ex) {
                    // Validation Error
                    throw new javax.validation.ConstraintViolationException("Validation Fail!", ex.getConstraintViolations());

                } catch (Exception e) {
                    System.out.println("Serial Service : " + e.getLocalizedMessage() );
                    return new ResponseObject(500 , e.getLocalizedMessage() ,isSuccess);
                }
            }
        }
        String message = (isSuccess == true) ? "Update Serial Success!": "Serial is Available, update Serial fail!";

        return new ResponseObject(status, message, isSuccess);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseObject deleteSerial(long id) {

        boolean checkSerial = serialRepository.existsById(id);
        int status = 501;

            if(checkSerial == true) {
                try {

                serialRepository.deleteById(id);
                status = 200;

                } catch (Exception e) {
                    System.out.println("Serial Service : " + e.getLocalizedMessage());
                    return new ResponseObject(500 , e.getLocalizedMessage() ,false);
                }
            }
            String message =  (checkSerial == true) ? "Delete Serial Success!": "Serial not Available, Delete Serial Fail!";

        return new ResponseObject(status, message, checkSerial);
    }
}
