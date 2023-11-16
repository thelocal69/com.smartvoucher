package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.converter.TicketConverter;
import com.smartvoucher.webEcommercesmartvoucher.converter.TicketHistoryConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.*;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ExpiredVoucherException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.entity.TicketEntity;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.*;
import com.smartvoucher.webEcommercesmartvoucher.service.ITicketService;
import com.smartvoucher.webEcommercesmartvoucher.util.EmailUtil;
import com.smartvoucher.webEcommercesmartvoucher.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                , EmailUtil emailUtil) {
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
    public ResponseObject insertTicket(TicketDTO ticketDTO,String userEmail) throws MessagingException, UnsupportedEncodingException {
        if (checkExistsObject(ticketDTO)) {
            // kiểm tra ticket có duplicate ở database
            if (checkDuplicateTicket(ticketDTO)) {
                    TicketEntity ticket =
                            ticketRepository.save(
                                    ticketConverter.insertTicket(
                                            ticketDTO
                                            ,createSerial(ticketDTO)
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
                    this.emailUtil.sendTicketCode(userEmail,ticket.getIdSerial().getSerialCode());
                    // phía fe get mã serial code trả về cho user thấy
                    return new ResponseObject(200,
                            "Buy Ticket success",
                            ticketConverter.toTicketDTO(ticket));
            } else {
                throw new DuplicationCodeException(400,
                        "Duplicate Serial or Category or Order");
            }
        } else {
            throw new ObjectEmptyException(406,
                    "Serial, Warehouse, Category, Order, User, Store is empty, please check and fill all data");
        }
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
            ticketHistoryRepository.delete(
                    ticketHistoryRepository.findBySerialCode(ticket.getIdSerial().getSerialCode()));
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
                    ticketHistoryRepository.save(
                            ticketHistoryConverter.updateStatusTicketHistory(
                                    ticketHistoryRepository.findBySerialCode(serialCode),2));
                        ticketEntity.setStatus(2);
                        ticketEntity.setRedeemedtimeTime(presentTime());
                        ticketRepository.save(ticketEntity);
                        // Khi user bấm mua rồi thì ticket này không thể sử dụng được nữa
                        return new ResponseObject(200, "Used Ticket Success !", true);
                } else {
                    throw new ExpiredVoucherException(410, "Expired Voucher !");
                }
        } else {
            throw new ObjectNotFoundException(404, "Ticket not found, pls check and try again!");
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
        Optional<WareHouseEntity> wareHouseEntity = iWareHouseRepository.findById(ticketDTO.getIdWarehouseDTO().getId());
        Optional<CategoryEntity> categoryEntity = iCategoryRepository.findById(ticketDTO.getIdCategoryDTO().getId());
        Optional<OrderEntity> orderEntity = orderRepository.findById(ticketDTO.getIdOrderDTO().getId());
        Optional<UserEntity> userEntity = userRepository.findById(ticketDTO.getIdUserDTO().getId());
        Optional<StoreEntity> storeEntity = storeRepository.findById(ticketDTO.getIdStoreDTO().getId());
        return  wareHouseEntity.isPresent() &&
                categoryEntity.isPresent() &&
                orderEntity.isPresent() &&
                userEntity.isPresent() &&
                storeEntity.isPresent();
    }

    public boolean checkDuplicateTicket(TicketDTO ticketDTO) {
        return ticketRepository.findByIdSerialOrIdCategoryOrIdOrder(
                createSerial(ticketDTO),
                createCategory(ticketDTO),
                createOrder(ticketDTO)).isEmpty();
    }

    public SerialEntity createSerial(TicketDTO ticketDTO) {
        return serialRepository.findById(ticketDTO.getIdSerialDTO().getId()).orElse(null);
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

