package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.OrderDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;

import java.util.List;

public interface IOrderService {
    ResponseObject getAllOrder();
    ResponseObject insertOrder(OrderDTO orderDTO);
//    ResponseObject updateOrder(OrderDTO orderDTO);
    ResponseObject deleteOrder(long id);
    List<OrderDTO> getAllOrderByIdUser(long id);
}
