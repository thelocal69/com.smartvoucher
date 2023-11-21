package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.OrderConverter;
import com.smartvoucher.webEcommercesmartvoucher.converter.UserConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.OrderDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.*;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.*;
import com.smartvoucher.webEcommercesmartvoucher.service.IOrderService;
import com.smartvoucher.webEcommercesmartvoucher.util.RandomCodeHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final UserRepository userRepository;
    private final IWareHouseRepository iWareHouseRepository;
    private final UserConverter userConverter;
    private final RandomCodeHandler randomCodeHandler;
    private final IStoreRepository iStoreRepository;
    private final IDiscountTypeRepository iDiscountTypeRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository
            , OrderConverter orderConverter
            , UserRepository userRepository
            , IWareHouseRepository iWareHouseRepository
            , UserConverter userConverter
            , RandomCodeHandler randomCodeHandler,
                        IStoreRepository istoreRepository,
                        IDiscountTypeRepository iDiscountTypeRepository) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
        this.userRepository = userRepository;
        this.iWareHouseRepository = iWareHouseRepository;
        this.userConverter = userConverter;
        this.randomCodeHandler = randomCodeHandler;
        this.iStoreRepository =istoreRepository;
        this.iDiscountTypeRepository = iDiscountTypeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseObject getAllOrder(){
        List<OrderDTO> listOrder = new ArrayList<>();
        List<OrderEntity> list = orderRepository.findAll();
        if(!list.isEmpty()) {
            for (OrderEntity data : list) {
                listOrder.add(orderConverter.toOrdersDTO(data));
            }
            log.info("Get all order successfully!");
            return new ResponseObject(200,
                    "List Order",
                            listOrder);
        } else {
            log.info("List Order is empty");
            throw new ObjectNotFoundException(404, "List Order is empty");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject insertOrder(OrderDTO orderDTO){
        String orderNoRandom = randomCodeHandler.generateRandomChars(10);
        OrderEntity order = orderRepository.findByOrderNo(orderNoRandom);
        if (order == null) {
                if(existsUserAndWarehouse(orderDTO)) {
                    log.info("Add Order success");
                    return new ResponseObject(200,
                            "Add Order success",
                            orderConverter.toOrdersDTO(orderRepository.save(
                                    orderConverter.insertOrder(
                                            orderDTO
                                            ,createUser(orderDTO)
                                            ,createWareHouse(orderDTO)
                                            ,orderNoRandom))) );
                }else {
                    log.info("User Or Warehouse is empty, please fill all data, add order fail");
                    throw new ObjectEmptyException(406,
                            "User Or Warehouse is empty, please fill all data, add order fail");
                }
        } else {
            log.info("Order is available, add order fail");
            throw new DuplicationCodeException(400, "Order is available, add order fail");
        }
    }

    @Override
    public OrderDTO insertOder(OrderDTO orderDTO) {
        String oderCode = UUID.randomUUID().toString().substring(0, 20).replace("-","");
        OrderEntity order = orderRepository.findByOrderNo(oderCode);
        if (order==null){
            if (existsUserAndWarehouseAndStoreAndDiscount(orderDTO)){
                log.info("Add Order success");
                UserEntity user = userRepository.findOneById(orderDTO.getIdUser());
                WareHouseEntity wareHouse = iWareHouseRepository.findOneById(orderDTO.getIdWarehouse());
                StoreEntity store = iStoreRepository.findOneById(orderDTO.getIdStore());
                DiscountTypeEntity discountType = iDiscountTypeRepository.findOneByName(orderDTO.getDiscountName());
                OrderEntity order1 = new OrderEntity();
                order1.setOrderNo(oderCode);
                order1.setIdUser(user);
                order1.setIdWarehouse(wareHouse);
                this.orderRepository.save(order1);
                return orderConverter.toOrderDTO(order1, store, discountType);
            }else {
                log.info("User Or Warehouse is empty, please fill all data, add order fail");
                throw new ObjectEmptyException(406,
                        "User Or Warehouse is empty, please fill all data, add order fail");
            }
        }else {
            log.info("Order is available, add order fail");
            throw new DuplicationCodeException(400, "Order is available, add order fail");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject deleteOrder(long id){
        OrderEntity role = orderRepository.findById(id).orElse(null);
        if(role != null) {
            orderRepository.deleteById(id);
            log.info("Delete Order Success");
            return new ResponseObject(200, "Delete Order Success", true);
        } else {
            log.info("Can not delete Order id : " + id);
            throw new ObjectNotFoundException(404, "Can not delete Order id : " + id);
        }
    }

    public boolean existsUserAndWarehouse(OrderDTO orderDTO) {
        Optional<UserEntity> usersEntity = userRepository.findById(orderDTO.getIdUserDTO().getId());
        Optional<WareHouseEntity> wareHouseEntity = iWareHouseRepository.findById(orderDTO.getIdWarehouseDTO().getId());
        return usersEntity.isPresent() && wareHouseEntity.isPresent();
    }
    public boolean existsUserAndWarehouseAndStoreAndDiscount(OrderDTO orderDTO) {
        boolean usersEntity = userRepository.existsById(orderDTO.getIdUser());
        boolean wareHouseEntity = iWareHouseRepository.existsById(orderDTO.getIdWarehouse());
        boolean store = iStoreRepository.existsById(orderDTO.getIdStore());
        boolean discount = iDiscountTypeRepository.existsByName(orderDTO.getDiscountName());
        return usersEntity && wareHouseEntity && store && discount;
    }
    public UserEntity createUser(OrderDTO orderDTO) {
        return userRepository.findById(orderDTO.getIdUserDTO().getId()).orElse(null);
    }
    public WareHouseEntity createWareHouse(OrderDTO orderDTO) {
        return iWareHouseRepository.findById(orderDTO.getIdWarehouseDTO().getId()).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<OrderDTO> getAllOrderByIdUser(UserDTO userDTO){
        List<OrderEntity> getAllOrder = orderRepository.findByEmail(userDTO.getEmail());
        if(getAllOrder.isEmpty()){
            log.info("All orders of user "  + userDTO.getEmail() + " is empty!");
            throw new ObjectNotFoundException(404, "All orders of user "  + userDTO.getEmail() + " is empty!");
        }else {
            log.info("Get all orders of user " +  userDTO.getEmail() + " is completed  !");
            return orderConverter.orderDTOList(getAllOrder);
        }
    }

}
