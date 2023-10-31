package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.SerialConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.CheckCapacityException;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.IWareHouseRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.SerialRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.WarehouseSerialRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.ISerialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SerialService implements ISerialService {
    private final SerialRepository serialRepository;
    private final SerialConverter serialConverter;
    private final WarehouseSerialRepository warehouseSerialRepository;
    private final IWareHouseRepository wareHouseRepository;
    @Autowired
    public SerialService(SerialRepository serialRepository,
                         SerialConverter serialConverter,
                         WarehouseSerialRepository warehouseSerialRepository,
                         IWareHouseRepository wareHouseRepository) {
        this.serialRepository = serialRepository;
        this.serialConverter = serialConverter;
        this.warehouseSerialRepository = warehouseSerialRepository;
        this.wareHouseRepository = wareHouseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseObject getAllSerial() {
        List<SerialDTO> listSerial = new ArrayList<>();
        List<SerialEntity> list = serialRepository.findAll();
        if(!list.isEmpty()) {
            for (SerialEntity data : list) {
                listSerial.add(serialConverter.toSerialDTO(data));
            }
            return new ResponseObject(200, "List Serial", listSerial);
        } else{
            throw new ObjectNotFoundException(404, "List Serial is empty");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject insertSerial(SerialDTO serialDTO, long idWarehouse) {
        SerialEntity checkSerial = serialRepository.findBySerialCode(serialDTO.getSerialCode());
        if(checkSerial == null){
            WareHouseEntity wareHouseEntity = wareHouseRepository.findOneById(idWarehouse);
            if(wareHouseEntity != null) {
                int total = warehouseSerialRepository.total(wareHouseEntity);
                if(wareHouseEntity.getCapacity() - total > 0 ) {
                    return new ResponseObject(200,
                            "Add serial success!",
                            serialConverter.toSerialDTO(
                                    serialRepository.save(serialConverter.insertSerial(serialDTO))) );
                } else {
                    throw new CheckCapacityException(406, "Capacity is full, pls check and try again !");
                }
            } else {
                throw new ObjectNotFoundException(404, "Warehouse not found, pls try again");
            }
        } else {
            throw new DuplicationCodeException(400, "Serial is available, add fail!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject updateSerial(SerialDTO serialDTO) {
        SerialEntity oldSerial = serialRepository.findBySerialCodeAndId(serialDTO.getSerialCode(), serialDTO.getId());
            if (oldSerial != null){
                return new ResponseObject(200,
                                "Update Serial success",
                                serialConverter.toSerialDTO(
                                        serialRepository.save(serialConverter.updateSerial(serialDTO, oldSerial))) );
            } else {
                throw new ObjectNotFoundException(404, "Serial not found, update Serial fail!");
            }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject deleteSerial(long id){
        SerialEntity serial = serialRepository.findById(id).orElse(null);
            if(serial != null) {
                serialRepository.deleteById(id);
                return new ResponseObject(200, "Delete Serial Success", true);
            } else {
                throw new ObjectNotFoundException(404, "Can not delete Serial id : " + id);
            }
    }
}
