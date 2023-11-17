package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.OrderDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
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

    @GetMapping("/api/list-order")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllOrder(){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.ordersService.getAllOrder());
    }

    @PostMapping("/api/add-order")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> insertOrder(@RequestBody @Valid OrderDTO orderDTO){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.ordersService.insertOrder(orderDTO));
    }

    @DeleteMapping("/api/delete-order")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteOrder(@RequestParam long id){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.ordersService.deleteOrder(id));
    }

    @GetMapping("/api/get_all_order")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllOrderByUser(@RequestBody @Valid UserDTO userDTO){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "All order of user " + userDTO.getEmail() + " is below:",
                        this.ordersService.getAllOrderByIdUser(userDTO)
                )
        );
    }




}
