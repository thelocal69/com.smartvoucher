package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.OrderDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private IOrderService ordersService;

    public OrderController(IOrderService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllOrder() throws Exception {

        ResponseObject responseObject = ordersService.getAllOrder();

        return ResponseEntity.status(responseObject.getStatusCode()).body(responseObject);
    }

    @PostMapping
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> insertOrder(@RequestBody OrderDTO orderDTO) throws Exception {

        ResponseObject responseObject = ordersService.insertOrder(orderDTO);

        return ResponseEntity.status(responseObject.getStatusCode()).body(responseObject);
    }

    @PutMapping
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> updateOrder(@RequestBody OrderDTO orderDTO) throws Exception {

        ResponseObject responseObject = ordersService.updateOrder(orderDTO);

        return ResponseEntity.status(responseObject.getStatusCode()).body(responseObject);
    }

    @DeleteMapping
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> deleteOrder(@RequestParam long id) throws Exception{

        ResponseObject responseObject = ordersService.deleteOrder(id);

        return ResponseEntity.status(responseObject.getStatusCode()).body(responseObject);
    }

}
