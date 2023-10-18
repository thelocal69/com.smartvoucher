package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.OrderConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.OrderDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.OrderEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.IWareHouseRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.OrderRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final UserRepository userRepository;
    private final IWareHouseRepository iWareHouseRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository
            , OrderConverter orderConverter
            , UserRepository userRepository
            , IWareHouseRepository iWareHouseRepository ) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
        this.userRepository = userRepository;
        this.iWareHouseRepository = iWareHouseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseObject getAllOrder(){
        List<OrderDTO> listOrder = new ArrayList<>();
        try {
            List<OrderEntity> list = orderRepository.findAll();
            for (OrderEntity data : list) {
                listOrder.add(orderConverter.toOrdersDTO(data));
            }
        } catch (Exception e) {
            System.out.println("Order Service : " + e.getLocalizedMessage());
            return new ResponseObject(500, e.getLocalizedMessage(), "Not found List Orders !");
        }

        return new ResponseObject(200, "List Orders", listOrder );
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseObject insertOrder(@NotNull OrderDTO orderDTO){
        boolean isSuccess = false;
        int status = 501;
        Optional<OrderEntity> order = orderRepository.findByOrderNo(orderDTO.getOrderNo());

        if (order.isEmpty()) {
            try {
                // idUserDTO and idWarehouse of ordersDTO: Not null (compulsory)
                Optional<UserEntity> usersEntity = userRepository.findById(orderDTO.getIdUserDTO().getId());
                Optional<WareHouseEntity> wareHouseEntity = iWareHouseRepository.findById(orderDTO.getIdWarehouseDTO().getId());

                if(usersEntity.orElse(null) != null
                        && wareHouseEntity.orElse(null) != null) {
                    orderRepository.save(orderConverter.insertRole(orderDTO
                            ,usersEntity.orElse(null)
                            ,wareHouseEntity.orElse(null)));
                    isSuccess = true;
                    status = 200;
                }

            } catch (javax.validation.ConstraintViolationException ex) {
                throw new javax.validation.ConstraintViolationException("Validation Fail!", ex.getConstraintViolations());

            } catch (Exception e) {
                System.out.println("Order service : " + e.getLocalizedMessage() );
                return new ResponseObject(500, "Add Order fail!", isSuccess);
            }
        }
        String message = (isSuccess == true) ? "Add Order success!":"Add Order fail!";
        return new ResponseObject(status, message, isSuccess);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseObject updateOrder(@NotNull OrderDTO orderDTO){
        boolean isSuccess = false;
        int status = 501;
            Optional<OrderEntity> oldOrder = orderRepository.findById(orderDTO.getId());
            List<OrderEntity> checkOrder = orderRepository.findByOrderNoAndId(orderDTO.getOrderNo(), orderDTO.getId());

            if (!oldOrder.isEmpty() && checkOrder.isEmpty() && orderDTO.getQuantity() > 0) {
                try {
                    // idUserDTO and idWarehouse of ordersDTO: Not null (compulsory)
                    Optional<UserEntity> usersEntity = userRepository.findById(orderDTO.getIdUserDTO().getId());
                    Optional<WareHouseEntity> wareHouseEntity = iWareHouseRepository.findById(orderDTO.getIdWarehouseDTO().getId());

                    if(usersEntity.orElse(null) != null
                            && wareHouseEntity.orElse(null) != null) {
                        orderRepository.save(orderConverter.updateRole(orderDTO
                                , oldOrder.orElse(null)
                                , usersEntity.orElse(null)
                                , wareHouseEntity.orElse(null)));
                        isSuccess = true;
                        status = 200;
                    }
                } catch (javax.validation.ConstraintViolationException ex) {
                    throw new javax.validation.ConstraintViolationException("Validation Fail!", ex.getConstraintViolations());

                } catch (Exception e) {
                    System.out.println("Order Service : " + e.getLocalizedMessage());
                    return new ResponseObject(500, "update Order fail!", false);
                }
            }
        String message = (isSuccess == true) ? "Update Order Success!": "update Order fail!";
        return new ResponseObject(status, message, isSuccess);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseObject deleteOrder(@NotNull long id){
        boolean checkOrder = orderRepository.existsById(id);
        int status = 501;

        if(checkOrder == true) {
            try {
                orderRepository.deleteById(id);
                status = 200;

            } catch (Exception e) {
                System.out.println("Order Service : " + e.getLocalizedMessage());
                return new ResponseObject(500 , e.getLocalizedMessage() ,false);
            }
        }
        String message = (checkOrder == true) ? "Delete Order Success!": "Order not Available, Delete Order Fail!";
        return new ResponseObject(status, message, checkOrder);
    }


}
