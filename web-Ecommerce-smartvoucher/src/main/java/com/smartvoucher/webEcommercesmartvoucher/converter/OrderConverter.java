package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.OrderDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.OrderEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
            orderDTO.setDiscountName(orderEntity.getIdWarehouse().getDiscountType().getName());
            orderDTO.setCreatedAt(orderEntity.getCreatedAt());
            orderDTO.setUpdatedAt(orderEntity.getUpdatedAt());
            orderDTO.setCreatedBy(orderEntity.getCreatedBy());
            orderDTO.setUpdatedBy(orderEntity.getUpdatedBy());
        return orderDTO;
    }


    public OrderEntity insertOrder(OrderDTO orderDTO, UserEntity idUser, WareHouseEntity idWareHouse, String orderNoRandom) {
        OrderEntity order = new OrderEntity();
        order.setOrderNo(orderNoRandom);
        order.setStatus(1);
        order.setIdUser(idUser);
        order.setIdWarehouse(idWareHouse);
        order.setQuantity(orderDTO.getQuantity());
        return order;
    }

    public OrderEntity updateRole(OrderDTO orderDTO, OrderEntity oldOrder, UserEntity idUser, WareHouseEntity idWareHouse) {
        if(!Objects.equals(orderDTO.getStatus(), oldOrder.getStatus())) {
            oldOrder.setStatus(orderDTO.getStatus());
        }
        if(!Objects.equals(orderDTO.getQuantity(), oldOrder.getQuantity())) {
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

    public List<OrderDTO> orderDTOList (List<OrderEntity> list){
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(OrderEntity data : list){
            orderDTOList.add(toOrdersDTO(data));
        }
        return orderDTOList;
    }
}
