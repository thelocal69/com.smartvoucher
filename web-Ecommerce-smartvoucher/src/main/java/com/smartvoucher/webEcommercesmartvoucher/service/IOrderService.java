package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.OrderDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.OrderEntity;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;

import java.security.Principal;
import java.util.List;

public interface IOrderService {
    ResponseObject getAllOrder();
    ResponseOutput getAllOrder(int page, int limit, String sortBy, String sortField, Principal connectedUser);
    OrderDTO getOrderDetail(long id);
    ResponseObject insertOrder(OrderDTO orderDTO);
//    ResponseObject updateOrder(OrderDTO orderDTO);
    ResponseObject deleteOrder(long id);
    List<OrderDTO> getAllOrderByIdUser(long id);
}
