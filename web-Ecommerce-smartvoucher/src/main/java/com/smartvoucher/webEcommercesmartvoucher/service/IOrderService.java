package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.OrderDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;

public interface IOrderService {

    ResponseObject getAllOrder() throws Exception;

    ResponseObject insertOrder(OrderDTO orderDTO) throws Exception;

    ResponseObject updateOrder(OrderDTO orderDTO) throws Exception;

    ResponseObject deleteOrder(long id) throws Exception;
}
