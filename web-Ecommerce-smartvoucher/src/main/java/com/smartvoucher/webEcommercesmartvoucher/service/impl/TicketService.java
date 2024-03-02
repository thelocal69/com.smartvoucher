package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.SerialConverter;
import com.smartvoucher.webEcommercesmartvoucher.converter.TicketConverter;
import com.smartvoucher.webEcommercesmartvoucher.converter.TicketHistoryConverter;
import com.smartvoucher.webEcommercesmartvoucher.converter.WareHouseConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.BuyTicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDetailDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.*;
import com.smartvoucher.webEcommercesmartvoucher.exception.*;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.repository.*;
import com.smartvoucher.webEcommercesmartvoucher.service.ITicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.*;

@Service
@Slf4j
public class TicketService implements ITicketService {
    private final TicketRepository ticketRepository;
    private final SerialRepository serialRepository;
    private final IWareHouseRepository iWareHouseRepository;
    private final ICategoryRepository iCategoryRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final TicketConverter ticketConverter;
    private final IStoreRepository storeRepository;
    private final TicketHistoryRepository ticketHistoryRepository;
    private final TicketHistoryConverter ticketHistoryConverter;
    private final SerialConverter serialConverter;
    private final WarehouseSerialRepository warehouseSerialRepository;
    private final IWareHouseRepository wareHouseRepository;
    private final WareHouseConverter wareHouseConverter;

    @Autowired
    public TicketService(TicketRepository ticketRepository
                , TicketConverter ticketConverter
                , SerialRepository serialRepository
                , IWareHouseRepository iWareHouseRepository
                , ICategoryRepository iCategoryRepository
                , OrderRepository orderRepository
                , UserRepository userRepository
                , IStoreRepository storeRepository
                , TicketHistoryRepository ticketHistoryRepository
                , TicketHistoryConverter ticketHistoryConverter
                , SerialConverter serialConverter
                ,  WarehouseSerialRepository warehouseSerialRepository
                , IWareHouseRepository wareHouseRepository
                , WareHouseConverter wareHouseConverter) {
        this.ticketRepository = ticketRepository;
        this.ticketConverter = ticketConverter;
        this.serialRepository = serialRepository;
        this.iWareHouseRepository = iWareHouseRepository;
        this.iCategoryRepository = iCategoryRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.ticketHistoryRepository = ticketHistoryRepository;
        this.ticketHistoryConverter = ticketHistoryConverter;
       this.serialConverter = serialConverter;
        this.warehouseSerialRepository = warehouseSerialRepository;
        this.wareHouseRepository = wareHouseRepository;
        this.wareHouseConverter = wareHouseConverter;
    }

    @Override
    public ResponseObject getAllTicket() {
        List<TicketDTO> listTicket = new ArrayList<>();
        List<TicketEntity> list = ticketRepository.findAll();
        if(!list.isEmpty()) {
            for (TicketEntity data : list) {
                listTicket.add(ticketConverter.toTicketDTO(data));
            }
            log.info("Get all ticket is completed !");
            return new ResponseObject(200, "List Ticket", listTicket );
        } else {
            log.info("List Ticket is empty");
            throw new ObjectNotFoundException(404, "List Ticket is empty");
        }
    }

    @Override
    public ResponseOutput getAllTicket(long id, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        OrderEntity order = orderRepository.findOneById(id);
        List<TicketDetailDTO> ticketDTODetailList = ticketConverter.listTicketDetailDTO(
                ticketRepository.findAllByIdOrder(order, pageable)
        );
        if (ticketDTODetailList.isEmpty()){
            log.info("List Ticket is empty");
            throw new ObjectNotFoundException(404, "List Ticket is empty");
        }
        int totalItem = ticketRepository.countByIdOrder(id);
        int totalPage = (int) Math.ceil((double) totalItem / limit);
        log.info("Get all ticket is completed !");
        return new ResponseOutput(
                page,
                totalItem,
                totalPage,
                ticketDTODetailList
        );
    }

    @Override
    public ResponseObject insertTicket(@NotNull BuyTicketDTO buyTicketDTO) {
        List<TicketDTO> listVoucher = new ArrayList<>();
        List<SerialEntity> listSerial =
                generateSerial(buyTicketDTO.getIdWarehouse()
                            , buyTicketDTO.getNumberOfSerial());
        if (checkExistsObject(buyTicketDTO)) {
            for (SerialEntity serialEntity : listSerial) {
                    // kiểm tra ticket có duplicate ở database
                    if (checkDuplicateTicket(serialEntity)) {
                        WareHouseEntity wareHouseEntity = wareHouseRepository.findOneById(buyTicketDTO.getIdWarehouse());
                        CategoryEntity categoryEntity = iCategoryRepository.findOneById(buyTicketDTO.getIdCategory());
                        OrderEntity orderEntity = orderRepository.findOneById(buyTicketDTO.getIdOrder());
                        UserEntity user = userRepository.findOneById(buyTicketDTO.getIdUser());
                        StoreEntity store = storeRepository.findOneById(buyTicketDTO.getIdStore());
                        TicketEntity ticket = ticketConverter.toBuyTicketEntity(buyTicketDTO, wareHouseEntity);
                        ticket.setIdSerial(serialEntity);
                        ticket.setIdWarehouse(wareHouseEntity);
                        ticket.setIdCategory(categoryEntity);
                        ticket.setIdOrder(orderEntity);
                        ticket.setIdUser(user);
                        ticket.setIdStore(store);
                        this.ticketRepository.save(ticket);
                        ticketHistoryRepository.save(
                                new TicketHistoryEntity(
                                        ticket,
                                        ticket.getStatus(),
                                        ticket.getStatus(),
                                        ticket.getIdSerial().getSerialCode()));

                        // save list voucher
                        listVoucher.add(ticketConverter.toTicketDTO(ticket));
                    } else {
                        log.info("Duplicate Serial or Order");
                        throw new DuplicationCodeException(400,
                                "Duplicate Serial or Order");
                    }
            }
        } else {
            log.info("Serial, Warehouse, Category, Order, User, Store is empty, please check and fill all data");
            throw new ObjectEmptyException(406,
                    "Serial, Warehouse, Category, Order, User, Store is empty, please check and fill all data");
        }
        log.info("Buy Ticket success");
        return new ResponseObject(200,
                "Buy Ticket success",
                listVoucher);
    }

    public List<SerialEntity> generateSerial(long idWarehouse, int numberOfSerial) {
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
                    String batchCode = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
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
        log.info("Generate list serial is completed !");
        return listSerial;
    }

    @Override
    public ResponseObject updateTicket(TicketDTO ticketDTO) {
        TicketEntity oldTicket = ticketRepository.findById(ticketDTO.getId()).orElse(null);
        if (oldTicket != null){
                ticketHistoryRepository.save(
                        ticketHistoryConverter.updateStatusTicketHistory(
                                ticketHistoryRepository.findBySerialCode(oldTicket.getIdSerial().getSerialCode()),
                                ticketDTO.getStatus()));
                log.info("Update Ticket success");
            return new ResponseObject(200, "Update Ticket success", ticketConverter.toTicketDTO(
                    ticketRepository.save(
                            ticketConverter.updateTicket(
                                    ticketDTO.getStatus(),
                                    oldTicket))));
        } else {
            log.info("Ticket not found, update Ticket fail");
            throw new ObjectNotFoundException(404, "Ticket not found, update Ticket fail");
        }
    }

    @Override
    public ResponseObject deleteTicket(long id) {
        TicketEntity ticket = ticketRepository.findById(id).orElse(null);
        if(ticket != null) {
            ticketHistoryRepository.deleteByIdTicket(ticket);
            ticketRepository.deleteById(id);
            log.info("Delete Ticket Success");
            return new ResponseObject(200, "Delete Ticket Success", true);
        } else {
            log.info("Can not delete Ticket id : " + id);
            throw new ObjectNotFoundException(404, "Can not delete Ticket id : " + id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String userUseTicket(String serialCode) {
        TicketEntity ticketEntity = ticketRepository.findBySerialCode(serialRepository.findBySerialCode(serialCode));
        if(ticketEntity != null) {
            if(checkExpiredVoucher(ticketEntity)){
                if(ticketEntity.getStatus() == 1) {
                    ticketHistoryRepository.save(
                            ticketHistoryConverter.updateStatusTicketHistory(
                                    ticketHistoryRepository.findBySerialCode(serialCode),2));
                    ticketEntity.setStatus(2);
                    ticketEntity.setRedeemedtimeTime(presentTime());
                    ticketRepository.save(ticketEntity);
                    // Khi user bấm mua rồi thì ticket này không thể sử dụng được nữa
                    log.info("Used Ticket Success !");
                    return serialCode;
                } else {
                    log.info("Voucher used !");
                    throw new UsedVoucherException(405, "Voucher used !");
                }
            } else {
                log.info("Expired Voucher !");
                throw new ExpiredVoucherException(410, "Expired Voucher !");
            }
        } else {
            log.info("Voucher code is wrong, pls check and try again!");
            throw new ObjectNotFoundException(404, "Voucher code is wrong, pls check and try again!");
        }
    }


    public Timestamp presentTime(){
        return new Timestamp(System.currentTimeMillis());
    }

    public boolean checkExpiredVoucher(TicketEntity ticketEntity) {
        // check present time so sánh với expired time của voucher
        return presentTime().before(ticketEntity.getExpiredTime());
    }

//    @Scheduled(cron = "0 0 * * * ?") // cứ mỗi 00:00 AM thì method này sẽ tự động chạy để kiểm tra hàng loạt ticket
//    public void checkExpiredAllVoucher() {
//        Timestamp presentTime = new Timestamp(System.currentTimeMillis());
//        for(TicketEntity ticketEntity : ticketRepository.findAll()) {
//            if(presentTime.equals(ticketEntity.getExpiredTime()) ||
//                    presentTime.after(ticketEntity.getExpiredTime())) {
//                TicketHistoryEntity ticketHistory = ticketHistoryRepository.findBySerialCode(ticketEntity.getIdSerial().getSerialCode());
//                ticketHistory.setIsLatest(3);
//                ticketEntity.setStatus(3);
//            }
//        }
//    }

    public boolean checkExistsObject(BuyTicketDTO buyTicketDTO) {
        List<String> listName = Arrays.asList("Warehouse", "Category" , "Order", "User", "Store");
        List<Optional<?>> listObject = Arrays.asList(
                            iWareHouseRepository.findById(buyTicketDTO.getIdWarehouse()),
                                iCategoryRepository.findById(buyTicketDTO.getIdCategory()),
                                orderRepository.findById(buyTicketDTO.getIdOrder()),
                                userRepository.findById(buyTicketDTO.getIdUser()),
                                storeRepository.findById(buyTicketDTO.getIdStore()));
        for(int i = 0; i < listObject.size(); i++ ) {
            if(listObject.get(i).isEmpty()) {
                throw new ObjectEmptyException(406, listName.get(i) + " is empty, pls check and try again !");
            }
        }
        if (listObject.get(0).isPresent()) {
            Optional<WareHouseEntity> wareHouseEntity = (Optional<WareHouseEntity>) listObject.get(0);
            // kiểm tra warehouseEntity nếu null trả về null nếu k null thì trả về đúng giá trị của nó
            if (Objects.requireNonNull(wareHouseEntity.orElse(null)).getStatus() != 1) {
                throw new CheckStatusWarehouseException(405, "Warehouse inactive !");
            }
        }
        return true;
    }

    public boolean checkDuplicateTicket(SerialEntity serialEntity) {
        return ticketRepository.findByIdSerial(serialEntity).isEmpty();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TicketDTO getTicketDetail(UserDTO userDTO){
        TicketEntity ticketDetail = ticketRepository.findByIdUser(userDTO.getId());
        if(ticketDetail != null){
            log.info("Get ticket detail of user " + userDTO.getUserName() + " is completed !");
            return ticketConverter.toTicketDTO(ticketDetail);
        }else{
            log.info("Ticket is not exist");
            throw new ObjectNotFoundException(404, "Ticket is not exist");
        }
    }

}

