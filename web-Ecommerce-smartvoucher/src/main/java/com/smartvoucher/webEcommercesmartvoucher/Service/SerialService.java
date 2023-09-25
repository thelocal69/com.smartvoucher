package com.smartvoucher.webEcommercesmartvoucher.Service;

import com.smartvoucher.webEcommercesmartvoucher.Converter.SerialEntityToDTO;
import com.smartvoucher.webEcommercesmartvoucher.DTO.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.Entity.SerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.Repository.SerialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SerialService {

    private final SerialRepository serialRepository;

    @Autowired
    private SerialEntityToDTO serialEntityToDTO;

    @Autowired
    public SerialService(SerialRepository serialRepository) {
        this.serialRepository = serialRepository;
    }

    public List<SerialDTO> findAllSerial() {

        List<SerialEntity> list = serialRepository.findAll();

        List<SerialDTO> listSerial = serialEntityToDTO.findAllSerial(list);

        return listSerial;
    }

    public boolean insertSerial(SerialEntity serialEntity) {

        boolean isSuccess = false;

        SerialEntity checkSerial = serialRepository.findSerialBySerialCode(serialEntity.getSerialCode());

        if(checkSerial == null){

            // lấy thời gian hiện tại set cho field createAt
            // không cho admin hoặc client tự nhập vào
            Date currentDate = new Date();
            Timestamp currentTimestamp = new Timestamp(currentDate.getTime());
            serialEntity.setCreatedAt(currentTimestamp);

            serialRepository.save(serialEntity);
            isSuccess = true;
        }

        return isSuccess;
    }


    public boolean updateSerial(SerialEntity serialEntity) {

        boolean checkUpdate = false;

        // orElse(null) : trả về Object, nếu như không tìm thấy đối tượng thì sẽ trả về null
        SerialEntity updateSerial = serialRepository.findById(serialEntity.getId()).orElse(null);


        if (updateSerial != null){

            if ((serialEntity.getBatchCode() != null)
                    && !serialEntity.getBatchCode().isEmpty()
                    && !Objects.equals(serialEntity.getBatchCode(), updateSerial.getBatchCode()) )
            {
                updateSerial.setBatchCode(serialEntity.getBatchCode());
            }

            if (serialEntity.getNumberOfSerial() > 0
                    && !Objects.equals(serialEntity.getNumberOfSerial(), updateSerial.getNumberOfSerial()) )
            {
                updateSerial.setNumberOfSerial(serialEntity.getNumberOfSerial());
            }

            if ((serialEntity.getSerialCode() != null)
                    && !serialEntity.getSerialCode().isEmpty()
                    && !Objects.equals(serialEntity.getSerialCode(), updateSerial.getSerialCode()) )
            {
                updateSerial.setSerialCode(serialEntity.getSerialCode());
            }

            if (serialEntity.getStatus() > 0
                    && !Objects.equals(serialEntity.getStatus(), updateSerial.getStatus()) )
            {
                updateSerial.setStatus(serialEntity.getStatus());
            }

            if ((serialEntity.getCreatedBy() != null)
                    && !serialEntity.getCreatedBy().isEmpty()
                    && !Objects.equals(serialEntity.getCreatedBy(), updateSerial.getCreatedBy()) )
            {
                updateSerial.setCreatedBy(serialEntity.getCreatedBy());
            }

            if ((serialEntity.getUpdatedBy() != null)
                    && !serialEntity.getUpdatedBy().isEmpty()
                    && !Objects.equals(serialEntity.getUpdatedBy(), updateSerial.getUpdatedBy()) )
            {
                updateSerial.setUpdatedBy(serialEntity.getUpdatedBy());
            }

            if (serialEntity.getCreatedAt() != null)
            {
                // currentTimestamp : lấy ra thời gian hiện tại
                Date currentDate = new Date();
                Timestamp currentTimestamp = new Timestamp(currentDate.getTime());

            // so sánh thời gian khi request tới : before or equals thời gian hiện tại
                if( (serialEntity.getCreatedAt().before(currentTimestamp) || serialEntity.getCreatedAt().equals(currentTimestamp))
                  && !Objects.equals(serialEntity.getCreatedAt(), updateSerial.getCreatedAt())) {

                    updateSerial.setCreatedAt(serialEntity.getCreatedAt());

                }

            }

            // lấy thời gian hiện tại để set cho field updateAt
            // không cho phía client hoặc admin tự update field này!
            Date currentDate = new Date();
            Timestamp currentTimestamp = new Timestamp(currentDate.getTime());
            updateSerial.setUpdatedAt(currentTimestamp);


            serialRepository.save(updateSerial);
            checkUpdate = true;
        }

        return checkUpdate;
    }

    public boolean deleteSerial(long id) {

        boolean checkSerial = serialRepository.existsById(id), deleteSerial = false;

        if(checkSerial == true) {
            serialRepository.deleteById(id);
            deleteSerial = true;
        }

        return deleteSerial;
    }
}
