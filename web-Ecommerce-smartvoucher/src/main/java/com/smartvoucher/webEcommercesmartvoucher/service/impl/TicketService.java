package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.converter.SerialConverter;
import com.smartvoucher.webEcommercesmartvoucher.converter.TicketConverter;
import com.smartvoucher.webEcommercesmartvoucher.converter.TicketHistoryConverter;
import com.smartvoucher.webEcommercesmartvoucher.converter.WareHouseConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.*;
import com.smartvoucher.webEcommercesmartvoucher.exception.*;
import com.smartvoucher.webEcommercesmartvoucher.entity.TicketEntity;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.*;
import com.smartvoucher.webEcommercesmartvoucher.service.ITicketService;
import com.smartvoucher.webEcommercesmartvoucher.util.EmailUtil;
import com.smartvoucher.webEcommercesmartvoucher.util.RandomCodeHandler;
import com.smartvoucher.webEcommercesmartvoucher.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.*;

@Service
@EnableScheduling
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
    private final UploadUtil uploadUtil;
    private final EmailUtil emailUtil;
    private final SerialConverter serialConverter;
    private final WarehouseSerialRepository warehouseSerialRepository;
    private final IWareHouseRepository wareHouseRepository;
    private final RandomCodeHandler randomCodeHandler;
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
                , UploadUtil uploadUtil
                , EmailUtil emailUtil
                , SerialConverter serialConverter
                ,  WarehouseSerialRepository warehouseSerialRepository
                , IWareHouseRepository wareHouseRepository
                , RandomCodeHandler randomCodeHandler
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
        this.uploadUtil = uploadUtil;
        this.emailUtil = emailUtil;
        this.serialConverter = serialConverter;
        this.warehouseSerialRepository = warehouseSerialRepository;
        this.wareHouseRepository = wareHouseRepository;
        this.randomCodeHandler = randomCodeHandler;
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
            return new ResponseObject(200, "List Ticket", listTicket );
        } else {
            throw new ObjectNotFoundException(404, "List Ticket is empty");
        }
    }

    @Override
    public ResponseObject insertTicket(@NotNull TicketDTO ticketDTO
            ,@NotNull String userEmail
            ,@NotNull int numberOfSerial) throws MessagingException, UnsupportedEncodingException {
        List<TicketDTO> listVoucher = new ArrayList<>();
        List<SerialEntity> listSerial =
                generateSerial(ticketDTO.getIdWarehouseDTO().getId()
                            ,numberOfSerial);
        if (checkExistsObject(ticketDTO)) {
            for (SerialEntity serialEntity : listSerial) {
                    // kiểm tra ticket có duplicate ở database
                    if (checkDuplicateTicket(serialEntity)) {
                        TicketEntity ticket =
                                ticketRepository.save(
                                        ticketConverter.insertTicket(
                                                ticketDTO
                                                ,serialEntity
                                                ,createWarehouse(ticketDTO)
                                                ,createCategory(ticketDTO)
                                                ,createOrder(ticketDTO)
                                                ,createUser(ticketDTO)
                                                ,createStore(ticketDTO)));
                        ticketHistoryRepository.save(
                                new TicketHistoryEntity(
                                        ticket,
                                        ticket.getStatus(),
                                        ticket.getStatus(),
                                        ticket.getIdSerial().getSerialCode()));

                        // save list voucher
                        listVoucher.add(ticketConverter.toTicketDTO(ticket));
                    } else {
                        throw new DuplicationCodeException(400,
                                "Duplicate Serial or Order");
                    }
            }
        } else {
            throw new ObjectEmptyException(406,
                    "Serial, Warehouse, Category, Order, User, Store is empty, please check and fill all data");
        }
        this.emailUtil.sendTicketCode(userEmail,listVoucher);
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
                    String batchCode = randomCodeHandler.generateRandomChars(10);
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
                            throw new DuplicationCodeException(400, "Serial is available, add fail!");
                        }
                    }
                } else {
                    throw new CheckCapacityException(406, "Current quantity is "+ (wareHouseEntity.getCapacity() - total) +" vouchers, pls check and try again !");
                }
            } else {
                throw new CheckStatusWarehouseException(405, "Warehouse inactive !");
            }
        }else {
            throw new ObjectNotFoundException(404, "Warehouse not found, pls check Warehouse and try again");
        }
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
            return new ResponseObject(200, "Update Ticket success", ticketConverter.toTicketDTO(
                    ticketRepository.save(
                            ticketConverter.updateTicket(
                                    ticketDTO.getStatus(),
                                    oldTicket))));
        } else {
            throw new ObjectNotFoundException(404, "Ticket not found, update Ticket fail");
        }
    }

    @Override
    public ResponseObject deleteTicket(long id) {
        TicketEntity ticket = ticketRepository.findById(id).orElse(null);
        if(ticket != null) {
            ticketHistoryRepository.deleteByIdTicket(ticket);
            ticketRepository.deleteById(id);
            return new ResponseObject(200, "Delete Ticket Success", true);
        } else {
            throw new ObjectNotFoundException(404, "Can not delete Ticket id : " + id);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseObject userUseTicket(String serialCode) {
        // kiểm tra mã serial của ticket có tồn tại trong csdl hay không
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
                        return new ResponseObject(200, "Used Ticket Success !", true);
                    } else {
                        throw new UsedVoucherException(405, "Voucher used !");
                    }
                } else {
                    throw new ExpiredVoucherException(410, "Expired Voucher !");
                }
        } else {
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

    @Scheduled(cron = "0 0 * * * ?") // cứ mỗi 00:00 AM thì method này sẽ tự động chạy để kiểm tra hàng loạt ticket
    public void checkExpiredAllVoucher() {
        Timestamp presentTime = new Timestamp(System.currentTimeMillis());
        for(TicketEntity ticketEntity : ticketRepository.findAll()) {
            if(presentTime.equals(ticketEntity.getExpiredTime()) ||
                    presentTime.after(ticketEntity.getExpiredTime())) {
                TicketHistoryEntity ticketHistory = ticketHistoryRepository.findBySerialCode(ticketEntity.getIdSerial().getSerialCode());
                ticketHistory.setIsLatest(3);
                ticketEntity.setStatus(3);
            }
        }
    }

    public boolean checkExistsObject(TicketDTO ticketDTO) {
        List<String> listName = Arrays.asList("Warehouse", "Category" , "Order", "User", "Store");
        List<Optional<?>> listObject = Arrays.asList(
                            iWareHouseRepository.findById(ticketDTO.getIdWarehouseDTO().getId()),
                                iCategoryRepository.findById(ticketDTO.getIdCategoryDTO().getId()),
                                orderRepository.findById(ticketDTO.getIdOrderDTO().getId()),
                                userRepository.findById(ticketDTO.getIdUserDTO().getId()),
                                storeRepository.findById(ticketDTO.getIdStoreDTO().getId()));
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

    public WareHouseEntity createWarehouse(TicketDTO ticketDTO) {
        return iWareHouseRepository.findById(ticketDTO.getIdWarehouseDTO().getId()).orElse(null);
    }
    public CategoryEntity createCategory(TicketDTO ticketDTO) {
        return iCategoryRepository.findById(ticketDTO.getIdCategoryDTO().getId()).orElse(null);
    }
    public OrderEntity createOrder(TicketDTO ticketDTO) {
        return orderRepository.findById(ticketDTO.getIdOrderDTO().getId()).orElse(null);
    }
    public UserEntity createUser(TicketDTO ticketDTO) {
        return userRepository.findById(ticketDTO.getIdUserDTO().getId()).orElse(null);
    }

    public StoreEntity createStore(TicketDTO ticketDTO) {
        return storeRepository.findById(ticketDTO.getIdStoreDTO().getId()).orElse(null);
    }

    @Override
    public File uploadTicketImages(MultipartFile fileName) {
        String folderId = "1D3tkdIWmKLQuRgdabrIfLYRkDeJyrflu";
        return uploadUtil.uploadImages(fileName, folderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TicketDTO getTicketDetail(UserDTO userDTO){
        TicketEntity ticketDetail = ticketRepository.findByIdUser(userDTO.getId());
        if(ticketDetail != null){
            return ticketConverter.toTicketDTO(ticketDetail);
        }else{
            throw new ObjectNotFoundException(404, "Ticket is not exist");
        }
    }

}

