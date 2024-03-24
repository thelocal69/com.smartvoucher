package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.OrderDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.OrderEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConverter {

    public OrderDTO toOrderDTO (OrderEntity orderEntity) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderEntity.getId());
        orderDTO.setIdWarehouse(orderEntity.getIdWarehouse().getId());
        orderDTO.setIdUser(orderEntity.getIdUser().getId());
        orderDTO.setOrderNo(orderEntity.getOrderNo());
        orderDTO.setStatus(orderEntity.getStatus());
        orderDTO.setQuantity(orderEntity.getQuantity());
        orderDTO.setEmail(orderEntity.getIdUser().getEmail());
        orderDTO.setPrice(orderEntity.getIdWarehouse().getPrice());
        orderDTO.setWarehouseName(orderEntity.getIdWarehouse().getName());
        orderDTO.setCreatedBy(orderEntity.getCreatedBy());
        orderDTO.setCreatedAt(orderEntity.getCreatedAt());
        orderDTO.setUpdatedBy(orderEntity.getUpdatedBy());
        orderDTO.setUpdatedAt(orderEntity.getUpdatedAt());
        return orderDTO;
    }

    public List<OrderDTO> toOrderDetailDTOList(List<OrderEntity> orderEntityList){
        return orderEntityList.stream().map(this::toOrderDTO).collect(Collectors.toList());
    }

    public OrderEntity toOrderEntity(OrderDTO orderDTO){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderDTO.getId());
        orderEntity.setOrderNo(orderDTO.getOrderNo());
        orderEntity.setStatus(orderDTO.getStatus());
        orderEntity.setQuantity(orderDTO.getQuantity());
        orderEntity.setCreatedBy(orderEntity.getCreatedBy());
        orderEntity.setCreatedAt(orderEntity.getCreatedAt());
        return orderEntity;
    }
}
