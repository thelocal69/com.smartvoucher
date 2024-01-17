package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.OrderConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.OrderDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.OrderEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.enums.Provider;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.repository.IWareHouseRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.OrderRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final UserRepository userRepository;
    private final IWareHouseRepository iWareHouseRepository;
    @Autowired
    public OrderService(OrderRepository orderRepository
            , OrderConverter orderConverter
            , UserRepository userRepository
            , IWareHouseRepository iWareHouseRepository) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
        this.userRepository = userRepository;
        this.iWareHouseRepository = iWareHouseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseObject getAllOrder(){
        List<OrderDTO> listOrder = new ArrayList<>();
        List<OrderEntity> list = orderRepository.findAll();
        if(!list.isEmpty()) {
            for (OrderEntity data : list) {
                listOrder.add(orderConverter.toOrderDTO(data));
            }
            return new ResponseObject(200,
                    "List Order",
                    listOrder);
        } else {
            throw new ObjectNotFoundException(404, "List Order is empty");
        }
    }

    @Override
    public ResponseOutput getAllOrder(int page, int limit, String sortBy, String sortField, Principal connectedUser) {
        Pageable pageable = PageRequest.of(
                page - 1, limit, Sort.by(Sort.Direction.fromString(sortBy), sortField)
        );
        UserEntity user = userRepository.findByEmailAndProvider(
                connectedUser.getName(), Provider.local.name()
        );
        List<OrderDTO> orderDetailDTOList = orderConverter.toOrderDetailDTOList(
                orderRepository.findAllByIdUser(user, pageable)
        );
        if(orderDetailDTOList.isEmpty()){
            log.info("All orders of user is empty!");
            throw new ObjectNotFoundException(404, "All orders of user is empty!");
        }
        int totalItem = orderRepository.countOrderByIdUser(user.getId());
        int totalPage = (int) Math.ceil((double) totalItem / limit);
        log.info("Get all orders of user is completed  !");
        return new ResponseOutput(
                page,
                totalItem,
                totalPage,
                orderDetailDTOList
        );
    }

    @Override
    public OrderDTO getOrderDetail(long id) {
        OrderDTO orderDTO = orderConverter.toOrderDTO(
                orderRepository.findOneById(id)
        );
        if (orderDTO == null){
            log.info("Order detail not found or not exist !");
            throw new ObjectNotFoundException(404, "Order detail not found or not exist !");
        }
        log.info("Get Order detail is completed !");
        return orderDTO;
    }

    @Override
    public OrderDTO insertOrder(OrderDTO orderDTO) {
        OrderEntity order = orderConverter.toOrderEntity(orderDTO);
        OrderDTO orderDTO1;
        if(existsUserAndWarehouse(orderDTO)){
            if (orderRepository.findByOrderNo(orderDTO.getOrderNo()) != null){
                log.info("Order no is duplication !");
                throw new DuplicationCodeException(500, "Order no is duplication !");
            }
            order.setIdUser(userRepository.findOneById(orderDTO.getIdUser()));
            order.setIdWarehouse(iWareHouseRepository.findOneById(orderDTO.getIdWarehouse()));
            orderDTO1 = orderConverter.toOrderDTO(
              orderRepository.save(order)
            );
        }else {
            log.info("User or Warehouse is not exist !");
            throw new ObjectNotFoundException(404, "User or Warehouse is not exist !");
        }
        return orderDTO1;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject deleteOrder(long id){
        OrderEntity role = orderRepository.findById(id).orElse(null);
        if(role != null) {
            orderRepository.deleteById(id);
            log.info("Delete Order Success");
            return new ResponseObject(200, "Delete Order Success", true);
        } else {
            log.info("Can not delete Order id : " + id);
            throw new ObjectNotFoundException(404, "Can not delete Order id : " + id);
        }
    }

    public boolean existsUserAndWarehouse(OrderDTO orderDTO) {
        boolean usersEntity = userRepository.existsById(orderDTO.getIdUser());
        boolean wareHouseEntity = iWareHouseRepository.existsById(orderDTO.getIdWarehouse());
        return usersEntity && wareHouseEntity;
    }
}
