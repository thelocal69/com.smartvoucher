package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.OrderDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.OrderEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderConverter {

    private final WareHouseConverter wareHouseConverter;
    private final UserConverter userConverter;

    @Autowired
    public OrderConverter(WareHouseConverter wareHouseConverter, UserConverter userConverter) {
        this.wareHouseConverter = wareHouseConverter;
        this.userConverter = userConverter;
    }

    public OrderDTO toOrdersDTO (OrderEntity orderEntity) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setIdWarehouseDTO(wareHouseConverter.toWareHouseDTO(orderEntity.getIdWarehouse()));
            orderDTO.setIdUserDTO(userConverter.toUserDTO(orderEntity.getIdUser()));
            orderDTO.setId(orderEntity.getId());
            orderDTO.setOrderNo(orderEntity.getOrderNo());
            orderDTO.setStatus(orderEntity.getStatus());
            orderDTO.setQuantity(orderEntity.getQuantity());
            orderDTO.setCreatedAt(orderEntity.getCreatedAt());
            orderDTO.setUpdatedAt(orderEntity.getUpdatedAt());
            orderDTO.setCreatedBy(orderEntity.getCreatedBy());
            orderDTO.setUpdatedBy(orderEntity.getUpdatedBy());
        return orderDTO;
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

    public OrderEntity insertRole(OrderDTO orderDTO, UserEntity idUser, WareHouseEntity idWareHouse) {
        OrderEntity orders = new OrderEntity();
        orders.setOrderNo(orderDTO.getOrderNo());
        orders.setStatus(1);
        orders.setIdUser(idUser);
        orders.setIdWarehouse(idWareHouse);
        orders.setQuantity(orderDTO.getQuantity());
        return orders;
    }

    public OrderEntity updateRole(OrderDTO orderDTO, OrderEntity oldOrder, UserEntity idUser, WareHouseEntity idWareHouse) {
        if(orderDTO.getOrderNo() != null
                && !orderDTO.getOrderNo().isEmpty()
                && !Objects.equals(orderDTO.getOrderNo(), oldOrder.getOrderNo())) {
            oldOrder.setOrderNo(orderDTO.getOrderNo());
        }
        if(orderDTO.getStatus() > 0
                && !Objects.equals(orderDTO.getStatus(), oldOrder.getStatus())) {
            oldOrder.setStatus(orderDTO.getStatus());
        }
        if(orderDTO.getQuantity() > 0
                && !Objects.equals(orderDTO.getQuantity(), oldOrder.getQuantity())) {
            oldOrder.setQuantity(orderDTO.getQuantity());
        }
        if(!Objects.equals(orderDTO.getIdUserDTO().getId(), oldOrder.getIdUser().getId()) && idUser != null) {
            oldOrder.setIdUser(idUser);
        }
        if(!Objects.equals(orderDTO.getIdWarehouseDTO().getId(), oldOrder.getIdWarehouse().getId()) && idWareHouse != null) {
            oldOrder.setIdWarehouse(idWareHouse);
        }
        return oldOrder;
    }
}
