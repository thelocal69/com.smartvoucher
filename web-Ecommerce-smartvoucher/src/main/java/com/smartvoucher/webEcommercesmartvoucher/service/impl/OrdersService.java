package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.OrdersConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.OrdersDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.OrdersEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.UsersEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.IWareHouseRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.OrdersRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.UsersRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersService implements IOrderService {

    private final OrdersRepository ordersRepository;
    private final OrdersConverter ordersConverter;
    private final UsersRepository usersRepository;
    private final IWareHouseRepository iWareHouseRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository
            ,OrdersConverter ordersConverter
            ,UsersRepository usersRepository
            ,IWareHouseRepository iWareHouseRepository ) {
        this.ordersRepository = ordersRepository;
        this.ordersConverter = ordersConverter;
        this.usersRepository = usersRepository;
        this.iWareHouseRepository = iWareHouseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseObject getAllOrder(){
        List<OrdersDTO> listOrder = new ArrayList<>();
        try {
            List<OrdersEntity> list = ordersRepository.findAll();
            for (OrdersEntity data : list) {
                listOrder.add(ordersConverter.toOrdersDTO(data));
            }
        } catch (Exception e) {
            System.out.println("Order Service : " + e.getLocalizedMessage());
            return new ResponseObject(500, e.getLocalizedMessage(), "Not found List Orders !");
        }

        return new ResponseObject(200, "List Orders", listOrder );
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseObject insertOrder(@NotNull OrdersDTO ordersDTO){
        boolean isSuccess = false;
        int status = 501;
        Optional<OrdersEntity> order = ordersRepository.findByOrderNo(ordersDTO.getOrderNo());

        if (order.isEmpty()) {
            try {
                // idUserDTO and idWarehouse of ordersDTO: Not null (compulsory)
                Optional<UsersEntity> usersEntity = usersRepository.findById(ordersDTO.getIdUserDTO().getId());
                Optional<WareHouseEntity> wareHouseEntity = iWareHouseRepository.findById(ordersDTO.getIdWarehouseDTO().getId());

                if(usersEntity.orElse(null) != null
                        && wareHouseEntity.orElse(null) != null) {
                    ordersRepository.save(ordersConverter.insertRole(ordersDTO
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
    public ResponseObject updateOrder(@NotNull OrdersDTO ordersDTO){
        boolean isSuccess = false;
        int status = 501;
            Optional<OrdersEntity> oldOrder = ordersRepository.findById(ordersDTO.getId());
            List<OrdersEntity> checkOrder = ordersRepository.findByOrderNoAndId(ordersDTO.getOrderNo(), ordersDTO.getId());

            if (!oldOrder.isEmpty() && checkOrder.isEmpty() && ordersDTO.getQuantity() > 0) {
                try {
                    // idUserDTO and idWarehouse of ordersDTO: Not null (compulsory)
                    Optional<UsersEntity> usersEntity = usersRepository.findById(ordersDTO.getIdUserDTO().getId());
                    Optional<WareHouseEntity> wareHouseEntity = iWareHouseRepository.findById(ordersDTO.getIdWarehouseDTO().getId());

                    if(usersEntity.orElse(null) != null
                            && wareHouseEntity.orElse(null) != null) {
                        ordersRepository.save(ordersConverter.updateRole(ordersDTO
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
        boolean checkOrder = ordersRepository.existsById(id);
        int status = 501;

        if(checkOrder == true) {
            try {
                ordersRepository.deleteById(id);
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
