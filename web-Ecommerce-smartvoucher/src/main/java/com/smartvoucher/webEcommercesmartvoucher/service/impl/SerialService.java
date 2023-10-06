package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.DTOtoEntity.SerialDTOtoEntity;
import com.smartvoucher.webEcommercesmartvoucher.converter.EntityToDTO.SerialEntityToDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.payload.BaseResponse;
import com.smartvoucher.webEcommercesmartvoucher.repository.SerialRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.ISerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class SerialService implements ISerialService {

    private final SerialRepository serialRepository;

    private SerialEntityToDTO serialEntityToDTO;

    private SerialDTOtoEntity serialDTOtoEntity;

    @Autowired
    public SerialService(SerialRepository serialRepository,
                         SerialEntityToDTO serialEntityToDTO,
                         SerialDTOtoEntity serialDTOtoEntity) {
        this.serialRepository = serialRepository;
        this.serialEntityToDTO = serialEntityToDTO;
        this.serialDTOtoEntity = serialDTOtoEntity;

    }

    @Override
    public BaseResponse getAllSerial() {

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(200);
        baseResponse.setMessage("List Serial");
        baseResponse.setData("Not found, List Serial is empty !");

        try {

            List<SerialEntity> list = serialRepository.findAll();
            List<SerialDTO> listSerial = serialEntityToDTO.findAllSerial(list);

            baseResponse.setData(listSerial);

        } catch (Exception e) {
            System.out.println("Serial Service : " + e.getLocalizedMessage());
        }

        return baseResponse;
    }

    @Override
    public BaseResponse insertSerial(SerialDTO serialDTO) {

        boolean isSuccess = false;
        BaseResponse baseResponse = new BaseResponse();

        // convert SerialDTO to SerialEntity
        SerialEntity serialEntity = serialDTOtoEntity.insertSerial(serialDTO);

        try {

            SerialEntity checkSerial = serialRepository.findSerialBySerialCode(serialEntity.getSerialCode());

            if(checkSerial == null){

                // get present time set field createAt
                // don't fill createAt by admin or client
                Date date = new Date();
                Timestamp currentTimestamp = new Timestamp(date.getTime());
                serialEntity.setCreatedAt(currentTimestamp);

                // fill default status number 1
                serialEntity.setStatus(1);

                serialRepository.save(serialEntity);
                isSuccess = true;

            }

        } catch (Exception e) {
            System.out.println("Serial Service : " + e.getLocalizedMessage() );
        }

        // set field BaseResponse
        baseResponse.setStatus(200);
        baseResponse.setMessage( isSuccess == true ? "Add serial success!":"Add serial fail!");
        baseResponse.setData(isSuccess);

        return baseResponse;
    }


    @Override
    public BaseResponse updateSerial(SerialDTO serialDTO) {

        boolean checkUpdate = false;
        BaseResponse baseResponse = new BaseResponse();

        try {
            // orElse(null) : return Object / if Object not found, will return null
            SerialEntity updateSerial = serialRepository.findById(serialDTO.getId()).orElse(null);

            if (updateSerial != null){

                if ((serialDTO.getBatchCode() != null)
                        && !serialDTO.getBatchCode().isEmpty()
                        && !Objects.equals(serialDTO.getBatchCode(), updateSerial.getBatchCode()) )
                {
                    updateSerial.setBatchCode(serialDTO.getBatchCode());
                }

                if (serialDTO.getNumberOfSerial() > 0
                        && !Objects.equals(serialDTO.getNumberOfSerial(), updateSerial.getNumberOfSerial()) )
                {
                    updateSerial.setNumberOfSerial(serialDTO.getNumberOfSerial());
                }

                if ((serialDTO.getSerialCode() != null)
                        && !serialDTO.getSerialCode().isEmpty()
                        && !Objects.equals(serialDTO.getSerialCode(), updateSerial.getSerialCode()) )
                {
                    updateSerial.setSerialCode(serialDTO.getSerialCode());
                }

                if (serialDTO.getStatus() > 0
                        && !Objects.equals(serialDTO.getStatus(), updateSerial.getStatus()) )
                {
                    updateSerial.setStatus(serialDTO.getStatus());
                }

                if ((serialDTO.getCreatedBy() != null)
                        && !serialDTO.getCreatedBy().isEmpty()
                        && !Objects.equals(serialDTO.getCreatedBy(), updateSerial.getCreatedBy()) )
                {
                    updateSerial.setCreatedBy(serialDTO.getCreatedBy());
                }

                if ((serialDTO.getUpdatedBy() != null)
                        && !serialDTO.getUpdatedBy().isEmpty()
                        && !Objects.equals(serialDTO.getUpdatedBy(), updateSerial.getUpdatedBy()) )
                {
                    updateSerial.setUpdatedBy(serialDTO.getUpdatedBy());
                }

                if (serialDTO.getCreatedAt() != null)
                {
                    // currentTimestamp : lấy ra thời gian hiện tại
                    Date currentDate = new Date();
                    Timestamp currentTimestamp = new Timestamp(currentDate.getTime());

                    // so sánh thời gian khi request tới : before or equals thời gian hiện tại
                    if( (serialDTO.getCreatedAt().before(currentTimestamp) || serialDTO.getCreatedAt().equals(currentTimestamp))
                            && !Objects.equals(serialDTO.getCreatedAt(), updateSerial.getCreatedAt())) {

                        updateSerial.setCreatedAt(serialDTO.getCreatedAt());

                    }

                }

                // get present time set field updateAt
                // don't fill updateAt by admin and client !
                Date currentDate = new Date();
                Timestamp currentTimestamp = new Timestamp(currentDate.getTime());
                updateSerial.setUpdatedAt(currentTimestamp);


                serialRepository.save(updateSerial);

                checkUpdate = true;

            }

        } catch (Exception e) {
            System.out.println("Serial Service : " + e.getLocalizedMessage() );
        }

        baseResponse.setStatus(200);
        baseResponse.setMessage( checkUpdate == true ? "Update Serial Success!": "Update Serial Fail!");
        baseResponse.setData(checkUpdate);

        return baseResponse;
    }

    @Override
    public BaseResponse deleteSerial(long id) {

        boolean checkSerial = serialRepository.existsById(id),
                deleteSerial = false;

        BaseResponse baseResponse = new BaseResponse();

        try {
            if(checkSerial == true) {
                serialRepository.deleteById(id);
                deleteSerial = true;
            }

        } catch (Exception e) {
            System.out.println("Serial Service : " + e.getLocalizedMessage());
        }

        baseResponse.setStatus(200);
        baseResponse.setMessage( deleteSerial == true ? "Delete Serial Success!": "Delete Serial Fail!");
        baseResponse.setData(deleteSerial);

        return baseResponse;
    }
}
