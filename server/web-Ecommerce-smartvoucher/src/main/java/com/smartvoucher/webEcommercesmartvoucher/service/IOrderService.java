package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.OrderDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;

import java.security.Principal;

public interface IOrderService {
    ResponseObject getAllOrder();
    ResponseOutput getAllOrder(int page, int limit, String sortBy, String sortField, Principal connectedUser);
    OrderDTO getOrderDetail(long id);
    OrderDTO insertOrder(OrderDTO orderDTO);
//    ResponseObject updateOrder(OrderDTO orderDTO);
    ResponseObject deleteOrder(long id);
}
