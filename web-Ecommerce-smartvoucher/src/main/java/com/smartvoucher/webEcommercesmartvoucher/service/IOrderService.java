package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.OrderDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;

public interface IOrderService {
    ResponseObject getAllOrder();
    ResponseObject insertOrder(OrderDTO orderDTO);
    ResponseObject updateOrder(OrderDTO orderDTO);
    ResponseObject deleteOrder(long id);
}
