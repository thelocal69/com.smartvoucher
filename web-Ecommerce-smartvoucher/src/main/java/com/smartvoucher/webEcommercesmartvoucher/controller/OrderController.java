package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.OrderDTO;
import com.smartvoucher.webEcommercesmartvoucher.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final IOrderService ordersService;

    @Autowired
    public OrderController(IOrderService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllOrder(){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.ordersService.getAllOrder());
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> insertOrder(@RequestBody @Valid OrderDTO orderDTO){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.ordersService.insertOrder(orderDTO));
    }

    @DeleteMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteOrder(@RequestParam long id){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.ordersService.deleteOrder(id));
    }

}
