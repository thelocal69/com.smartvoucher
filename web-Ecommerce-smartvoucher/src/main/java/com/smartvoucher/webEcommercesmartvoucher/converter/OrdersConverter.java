package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.OrdersDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UsersDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.WareHouseDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.OrdersEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.UsersEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class OrdersConverter {

    private final WareHouseConverter wareHouseConverter;
    private final UsersConverter usersConverter;

    @Autowired
    public OrdersConverter(WareHouseConverter wareHouseConverter, UsersConverter usersConverter) {
        this.wareHouseConverter = wareHouseConverter;
        this.usersConverter = usersConverter;
    }

    public OrdersDTO toOrdersDTO (OrdersEntity ordersEntity) {
            OrdersDTO ordersDTO = new OrdersDTO();
            ordersDTO.setIdWarehouseDTO(wareHouseConverter.toWareHouseDTO(ordersEntity.getIdWarehouse()));
            ordersDTO.setIdUserDTO(usersConverter.toUserDTO(ordersEntity.getIdUser()));
            ordersDTO.setId(ordersEntity.getId());
            ordersDTO.setOrderNo(ordersEntity.getOrderNo());
            ordersDTO.setStatus(ordersEntity.getStatus());
            ordersDTO.setQuantity(ordersEntity.getQuantity());
            ordersDTO.setCreatedAt(ordersEntity.getCreatedAt());
            ordersDTO.setUpdatedAt(ordersEntity.getUpdatedAt());
            ordersDTO.setCreatedBy(ordersEntity.getCreatedBy());
            ordersDTO.setUpdatedBy(ordersEntity.getUpdatedBy());
        return ordersDTO;
    }

//    public OrdersEntity toOrdersEntity(OrdersDTO ordersDTO) {
//        OrdersEntity orders = new OrdersEntity();
//        orders.setOrderNo(ordersDTO.getOrderNo());
//        orders.setStatus(1);
//        orders.setIdUser(usersConverter.toUserEntity(ordersDTO.getIdUserDTO()));
//        orders.setIdWarehouse(wareHouseConverter.toWareHouseEntity(ordersDTO.getIdWarehouseDTO()));
//        orders.setQuantity(ordersDTO.getQuantity());
//        return orders;
//    }

    public OrdersEntity insertRole(OrdersDTO ordersDTO, UsersEntity idUser, WareHouseEntity idWareHouse) {
        OrdersEntity orders = new OrdersEntity();
        orders.setOrderNo(ordersDTO.getOrderNo());
        orders.setStatus(1);
        orders.setIdUser(idUser);
        orders.setIdWarehouse(idWareHouse);
        orders.setQuantity(ordersDTO.getQuantity());
        return orders;
    }

    public OrdersEntity updateRole(OrdersDTO ordersDTO, OrdersEntity oldOrder, UsersEntity idUser, WareHouseEntity idWareHouse) {
        if(ordersDTO.getOrderNo() != null
                && !ordersDTO.getOrderNo().isEmpty()
                && !Objects.equals(ordersDTO.getOrderNo(), oldOrder.getOrderNo())) {
            oldOrder.setOrderNo(ordersDTO.getOrderNo());
        }
        if(ordersDTO.getStatus() > 0
                && !Objects.equals(ordersDTO.getStatus(), oldOrder.getStatus())) {
            oldOrder.setStatus(ordersDTO.getStatus());
        }
        if(ordersDTO.getQuantity() > 0
                && !Objects.equals(ordersDTO.getQuantity(), oldOrder.getQuantity())) {
            oldOrder.setQuantity(ordersDTO.getQuantity());
        }
        if(!Objects.equals(ordersDTO.getIdUserDTO().getId(), oldOrder.getIdUser().getId()) && idUser != null) {
            oldOrder.setIdUser(idUser);
        }
        if(!Objects.equals(ordersDTO.getIdWarehouseDTO().getId(), oldOrder.getIdWarehouse().getId()) && idWareHouse != null) {
            oldOrder.setIdWarehouse(idWareHouse);
        }
        return oldOrder;
    }
}
