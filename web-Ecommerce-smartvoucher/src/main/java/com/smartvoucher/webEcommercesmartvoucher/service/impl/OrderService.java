package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.OrderConverter;
import com.smartvoucher.webEcommercesmartvoucher.converter.UserConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.OrderDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.OrderEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.IWareHouseRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.OrderRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IOrderService;
import com.smartvoucher.webEcommercesmartvoucher.util.RandomCodeHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final UserRepository userRepository;
    private final IWareHouseRepository iWareHouseRepository;
    private final UserConverter userConverter;
    private final RandomCodeHandler randomCodeHandler;

    @Autowired
    public OrderService(OrderRepository orderRepository
            , OrderConverter orderConverter
            , UserRepository userRepository
            , IWareHouseRepository iWareHouseRepository
            , UserConverter userConverter
            , RandomCodeHandler randomCodeHandler) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
        this.userRepository = userRepository;
        this.iWareHouseRepository = iWareHouseRepository;
        this.userConverter = userConverter;
        this.randomCodeHandler = randomCodeHandler;
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
            return new ResponseObject(200,
                    "List Order",
                            listOrder);
        } else {
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
                    return new ResponseObject(200,
                            "Add Order success",
                            orderConverter.toOrdersDTO(orderRepository.save(
                                    orderConverter.insertOrder(
                                            orderDTO
                                            ,createUser(orderDTO)
                                            ,createWareHouse(orderDTO)
                                            ,orderNoRandom))) );
                }else {
                    throw new ObjectEmptyException(406,
                            "User Or Warehouse is empty, please fill all data, add order fail");
                }
        } else {
            throw new DuplicationCodeException(400, "Order is available, add order fail");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject deleteOrder(long id){
        OrderEntity role = orderRepository.findById(id).orElse(null);
        if(role != null) {
            orderRepository.deleteById(id);
            return new ResponseObject(200, "Delete Order Success", true);
        } else {
            throw new ObjectNotFoundException(404, "Can not delete Order id : " + id);
        }
    }

    public boolean existsUserAndWarehouse(OrderDTO orderDTO) {
        Optional<UserEntity> usersEntity = userRepository.findById(orderDTO.getIdUserDTO().getId());
        Optional<WareHouseEntity> wareHouseEntity = iWareHouseRepository.findById(orderDTO.getIdWarehouseDTO().getId());
        return usersEntity.isPresent() && wareHouseEntity.isPresent();
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
            throw new ObjectNotFoundException(404, "All order of user "  + userDTO.getEmail() + " is empty!");
        }else {
            return orderConverter.orderDTOList(getAllOrder);
        }
    }

}
