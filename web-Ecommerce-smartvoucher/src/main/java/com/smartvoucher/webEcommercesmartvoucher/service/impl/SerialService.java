package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.SerialConverter;
import com.smartvoucher.webEcommercesmartvoucher.converter.WareHouseConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WarehouseSerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.keys.WarehouseSerialKeys;
import com.smartvoucher.webEcommercesmartvoucher.exception.CheckCapacityException;
import com.smartvoucher.webEcommercesmartvoucher.exception.CheckStatusWarehouseException;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.*;
import com.smartvoucher.webEcommercesmartvoucher.service.ISerialService;
import com.smartvoucher.webEcommercesmartvoucher.util.RandomCodeHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SerialService implements ISerialService {
    private final TicketHistoryRepository ticketHistoryRepository;
    private final TicketRepository ticketRepository;
    private final SerialRepository serialRepository;
    private final SerialConverter serialConverter;
    private final WarehouseSerialRepository warehouseSerialRepository;
    private final IWareHouseRepository wareHouseRepository;
    private final RandomCodeHandler randomCodeHandler;
    private final WareHouseConverter wareHouseConverter;

    @Autowired
    public SerialService(SerialRepository serialRepository,
                         SerialConverter serialConverter,
                         WarehouseSerialRepository warehouseSerialRepository,
                         IWareHouseRepository wareHouseRepository,
                         RandomCodeHandler randomCodeHandler,
                         WareHouseConverter wareHouseConverter,
                         TicketHistoryRepository ticketHistoryRepository,
                         TicketRepository ticketRepository) {
        this.serialRepository = serialRepository;
        this.serialConverter = serialConverter;
        this.warehouseSerialRepository = warehouseSerialRepository;
        this.wareHouseRepository = wareHouseRepository;
        this.randomCodeHandler = randomCodeHandler;
        this.wareHouseConverter = wareHouseConverter;
        this.ticketRepository = ticketRepository;
        this.ticketHistoryRepository = ticketHistoryRepository;
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
            log.info("Get all serial is completed !");
            return new ResponseObject(200, "List Serial", listSerial);
        } else{
            log.info("List Serial is empty");
            throw new ObjectNotFoundException(404, "List Serial is empty");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject insertSerial(long idWarehouse, String batchCode, int numberOfSerial) {
        // list serial
        List<SerialEntity> listSerial = new ArrayList<>();
        // kiểm tra warehouse có tồn tại ở trong DB
        WareHouseEntity wareHouseEntity = wareHouseRepository.findOneById(idWarehouse);
        if(wareHouseEntity != null) {
            // kiểm tra status Warehouse = 1 (active) thì mới gen
            if(wareHouseEntity.getStatus() == 1) {
                // kiểm số lượng serial đã được gen
                int total = warehouseSerialRepository.total(wareHouseEntity);
                /* kiểm tra số lượng serial có thể gen tiếp tục,
                kiểm tra so với capacity (lấy capacity - total = total serial có thể gen tt)*/
                if(numberOfSerial <= (wareHouseEntity.getCapacity() - total)
                        && numberOfSerial <= wareHouseEntity.getCapacity() ) {
                    // generate số lượng Serial = numberOfSerial
                    for (int i = 0; i < numberOfSerial; i++ ) {
                        String serialCode = randomCodeHandler.generateRandomChars(10);
                        // kiểm tra mã serial code có duplicate ở trong DB
                        SerialEntity checkSerial = serialRepository.findBySerialCode(serialCode);
                        if(checkSerial == null){
                            SerialEntity serialEntity = serialRepository.save(serialConverter.generateSerial(batchCode, numberOfSerial, serialCode));
                            // save warehouse và serial vào table WarehouseSerial ( bảng trung gian )
                            wareHouseConverter.saveWarehouseSerial(serialEntity, wareHouseEntity);
                            listSerial.add(serialEntity);
                        } else {
                            log.info("Serial is available, add fail!");
                            throw new DuplicationCodeException(400, "Serial is available, add fail!");
                        }
                    }
                } else {
                    log.info("Current quantity is "+ (wareHouseEntity.getCapacity() - total) +" vouchers, pls check and try again !");
                    throw new CheckCapacityException(406, "Current quantity is "+ (wareHouseEntity.getCapacity() - total) +" vouchers, pls check and try again !");
                }
            } else {
                log.info("Warehouse inactive !");
                throw new CheckStatusWarehouseException(405, "Warehouse inactive !");
            }
        }else {
            log.info("Warehouse not found, pls check Warehouse and try again");
            throw new ObjectNotFoundException(404, "Warehouse not found, pls check Warehouse and try again");
        }
        log.info("Insert serial is completed !");
        return new ResponseObject(200,
                "Add serial success!",
                listSerial);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject updateSerial(SerialDTO serialDTO) {
        SerialEntity oldSerial = serialRepository.findBySerialCodeAndId(serialDTO.getSerialCode(), serialDTO.getId());
            if (oldSerial != null){
                log.info("Update Serial success");
                return new ResponseObject(200,
                                "Update Serial success",
                                serialConverter.toSerialDTO(
                                        serialRepository.save(serialConverter.updateSerial(serialDTO, oldSerial))) );
            } else {
                log.info("Serial not found, update Serial fail!");
                throw new ObjectNotFoundException(404, "Serial not found, update Serial fail!");
            }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject deleteSerial(long id){
        SerialEntity serial = serialRepository.findById(id).orElse(null);
            if(serial != null) {
                ticketHistoryRepository.deleteBySerialCode(serial.getSerialCode());
                ticketRepository.deleteByIdSerial(serial);
                warehouseSerialRepository.deleteByIdSerial(serial);
                serialRepository.deleteById(id);
                log.info("Delete Serial Success");
                return new ResponseObject(200, "Delete Serial Success", true);
            } else {
                log.info("Can not delete Serial id : " + id);
                throw new ObjectNotFoundException(404, "Can not delete Serial id : " + id);
            }
    }
}
