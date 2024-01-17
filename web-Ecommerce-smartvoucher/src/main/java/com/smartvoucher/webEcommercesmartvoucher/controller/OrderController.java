package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.OrderDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

@Slf4j
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
        log.info("Get all is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                this.ordersService.getAllOrder());
    }

    @PostMapping("/api/add-order")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertOrder(@RequestBody @Valid OrderDTO orderDTO){
        orderDTO.setOrderNo(
                UUID.randomUUID().toString().replace("-", "").substring(0,10)
        );
        log.info("Add is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Insert order is complete !",
                        this.ordersService.insertOrder(orderDTO)
                )
        );
    }


    @DeleteMapping("/api/delete-order")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteOrder(@RequestParam long id){
        log.info("Delete is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                this.ordersService.deleteOrder(id));
    }


    @GetMapping("/api/get/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getOrder(@PathVariable long id){
        log.info("All order is below: ");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "All order is below: ",
                        this.ordersService.getOrderDetail(id)
                )
        );
    }


    @GetMapping("/api/get/all")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseOutput> getOrderByUser(
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam String sortBy,
            @RequestParam String sortField,
            Principal connectedUser
    ){
        log.info("All order of user  is below: ");
        return new ResponseEntity<>(this.ordersService.getAllOrder(
                page, limit, sortBy, sortField, connectedUser), HttpStatus.OK);
    }

}
