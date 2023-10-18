package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.StoreConverter;
import com.smartvoucher.webEcommercesmartvoucher.converter.TicketConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.*;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.*;
import com.smartvoucher.webEcommercesmartvoucher.service.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService implements ITicketService {

    private final TicketRepository ticketRepository;

    private final SerialRepository serialRepository;
    private final IWareHouseRepository iWareHouseRepository;
    private final ICategoryRepository iCategoryRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final TicketConverter ticketConverter;
    private final IStoreRepository storeRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository
                ,TicketConverter ticketConverter
                ,SerialRepository serialRepository
                ,IWareHouseRepository iWareHouseRepository
                ,ICategoryRepository iCategoryRepository
                ,OrderRepository orderRepository
                ,UserRepository userRepository
                ,IStoreRepository storeRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketConverter = ticketConverter;
        this.serialRepository = serialRepository;
        this.iWareHouseRepository = iWareHouseRepository;
        this.iCategoryRepository = iCategoryRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
    }

    @Override
    public ResponseObject getAllTicket() {
        List<TicketDTO> listTicket = new ArrayList<>();
        try {
            List<TicketEntity> list = ticketRepository.findAll();
            for (TicketEntity data : list) {
                listTicket.add(ticketConverter.toTicketDTO(data));
            }

        } catch (Exception e) {
            System.out.println("Ticket Service : " + e.getLocalizedMessage());
            return new ResponseObject(500, e.getLocalizedMessage(), "Not found List Ticket !");
        }
        return new ResponseObject(200, "List Ticket", listTicket );
    }

    @Override
    public ResponseObject insertTicket(TicketDTO ticketDTO) {
        boolean isSuccess = false;
        int status = 501;

        SerialEntity serialEntity = serialRepository.findById(ticketDTO.getIdSerialDTO().getId()).orElse(null);
        WareHouseEntity wareHouseEntity = iWareHouseRepository.findById(ticketDTO.getIdWarehouseDTO().getId()).orElse(null);
        CategoryEntity categoryEntity = iCategoryRepository.findById(ticketDTO.getIdCategoryDTO().getId()).orElse(null);
        OrderEntity orderEntity = orderRepository.findById(ticketDTO.getIdOrderDTO().getId()).orElse(null);
        UserEntity userEntity = userRepository.findById(ticketDTO.getIdUserDTO().getId()).orElse(null);
        StoreEntity storeEntity = storeRepository.findOneById(ticketDTO.getIdStoreDTO().getId());

        if (serialEntity != null
                && wareHouseEntity != null
                && categoryEntity != null
                && orderEntity != null
                && userEntity != null
                && storeEntity != null) {
            try {
                Optional<TicketEntity> ticket =
                        ticketRepository.findByIdSerialOrIdCategoryOrIdOrder(serialEntity
                                ,categoryEntity
                                ,orderEntity);
                if (ticket.orElse(null) == null) {
                    ticketRepository.save(ticketConverter.insertTicket(ticketDTO
                                                                        , serialEntity
                                                                        , wareHouseEntity
                                                                        , categoryEntity
                                                                        , orderEntity
                                                                        , userEntity
                                                                        , storeEntity));
                    isSuccess = true;
                    status = 200;
                }
            } catch (javax.validation.ConstraintViolationException ex) {
                throw new javax.validation.ConstraintViolationException("Validation Fail!", ex.getConstraintViolations());

            } catch (Exception e) {
                System.out.println("Ticket service : " + e.getLocalizedMessage() );
                return new ResponseObject(500, "Add Ticket fail!", isSuccess);
            }
        }
        String message = isSuccess ? "Add Ticket success!":"Add Ticket fail!";
        return new ResponseObject(status, message, isSuccess);
    }

}
