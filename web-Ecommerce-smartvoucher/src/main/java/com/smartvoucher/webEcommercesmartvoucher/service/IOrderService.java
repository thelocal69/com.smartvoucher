package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.OrdersDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;

public interface IOrderService {

    ResponseObject getAllOrder() throws Exception;

    ResponseObject insertOrder(OrdersDTO ordersDTO) throws Exception;

    ResponseObject updateOrder(OrdersDTO ordersDTO) throws Exception;

    ResponseObject deleteOrder(long id) throws Exception;
}
