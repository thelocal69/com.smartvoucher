package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.SerialConverter;
import com.smartvoucher.webEcommercesmartvoucher.converter.WareHouseConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.*;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.repository.*;
import com.smartvoucher.webEcommercesmartvoucher.service.ISerialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class SerialService implements ISerialService {
    private final TicketHistoryRepository ticketHistoryRepository;
    private final TicketRepository ticketRepository;
    private final SerialRepository serialRepository;
    private final SerialConverter serialConverter;
    private final WarehouseSerialRepository warehouseSerialRepository;
    private final IWareHouseRepository wareHouseRepository;
    private final WareHouseConverter wareHouseConverter;

    @Autowired
    public SerialService(SerialRepository serialRepository,
                         SerialConverter serialConverter,
                         WarehouseSerialRepository warehouseSerialRepository,
                         IWareHouseRepository wareHouseRepository,
                         WareHouseConverter wareHouseConverter,
                         TicketHistoryRepository ticketHistoryRepository,
                         TicketRepository ticketRepository) {
        this.serialRepository = serialRepository;
        this.serialConverter = serialConverter;
        this.warehouseSerialRepository = warehouseSerialRepository;
        this.wareHouseRepository = wareHouseRepository;
        this.wareHouseConverter = wareHouseConverter;
        this.ticketRepository = ticketRepository;
        this.ticketHistoryRepository = ticketHistoryRepository;
    }

    @Override
    public ResponseOutput getAllSerial(int page, int limit, String sortBy, String sortField) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.fromString(sortBy), sortField));
        List<SerialDTO> serialDTOList = serialConverter.toSerialDTOList(
                serialRepository.findAll(pageable).getContent()
        );
        if (serialDTOList.isEmpty()){
            log.info("Get all serial is completed !");
            throw new ObjectEmptyException(406, "Get all serial is completed !");
        }
        int totalItem = (int) serialRepository.count();
        int totalPage = (int) Math.ceil((double) totalItem / limit);
        return new ResponseOutput(
                page,
                totalItem,
                totalPage,
                serialDTOList
        );
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
                        String serialCode = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
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
