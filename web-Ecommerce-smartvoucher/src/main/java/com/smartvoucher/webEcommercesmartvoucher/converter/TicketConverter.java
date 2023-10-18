package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.*;
import com.smartvoucher.webEcommercesmartvoucher.dto.OrderDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Component
public class TicketConverter {

    private final SerialConverter serialConverter;
    private final WareHouseConverter wareHouseConverter;
    private final CategoryConverter categoryConverter;
    private final OrderConverter orderConverter;
    private UserConverter userConverter;

    @Autowired
    public TicketConverter(SerialConverter serialConverter
                    ,WareHouseConverter wareHouseConverter
                    ,CategoryConverter categoryConverter
                    ,OrderConverter orderConverter
                    ,UserConverter userConverter) {
            this.categoryConverter = categoryConverter;
            this.wareHouseConverter = wareHouseConverter;
            this.serialConverter = serialConverter;
            this.orderConverter = orderConverter;
            this.userConverter = userConverter;
    }

    public TicketDTO toTicketDTO(TicketEntity ticketEntity) {
            TicketDTO ticketDTO = new TicketDTO(); // This is ticketDTO
            ticketDTO.setIdSerialDTO(serialConverter.toSerialDTO(ticketEntity.getIdSerial())); // set id serial
            ticketDTO.setIdWarehouseDTO(wareHouseConverter.toWareHouseDTO(ticketEntity.getIdWarehouse())); // set id warehouse
            ticketDTO.setIdCategoryDTO(categoryConverter.toCategoryDTO(ticketEntity.getIdCategory())); // set id category
            ticketDTO.setIdOrderDTO(orderConverter.toOrdersDTO(ticketEntity.getIdOrder())); // set id order
            ticketDTO.setIdUserDTO(userConverter.toUserDTO(ticketEntity.getIdUser())); // set id User
            ticketDTO.setId(ticketEntity.getId());
            ticketDTO.setClaimedTime(ticketEntity.getClaimedTime());
            ticketDTO.setRedeemedtimeTime(ticketEntity.getRedeemedtimeTime());
            ticketDTO.setExpiredTime(ticketEntity.getExpiredTime());
            ticketDTO.setDiscountType(ticketEntity.getDiscountType());
            ticketDTO.setDiscountAmount(ticketEntity.getDiscountAmount());
            ticketDTO.setBannerUrl(ticketEntity.getBannerUrl());
            ticketDTO.setThumbnailUrl(ticketEntity.getThumbnailUrl());
            ticketDTO.setAcquirerLogoUrl(ticketEntity.getAcquirerLogoUrl());
            ticketDTO.setTermOfUse(ticketEntity.getTermOfUse());
            ticketDTO.setDescription(ticketEntity.getDescription());
            ticketDTO.setVoucherChannel(ticketEntity.getVoucherChannel());
            ticketDTO.setAvailableFrom(ticketEntity.getAvailbleFrom());
            ticketDTO.setAvailableTo(ticketEntity.getAvaibleTo());
            ticketDTO.setAppliedStore(ticketEntity.getAppliedStore());
            ticketDTO.setStatus(1);
        return ticketDTO;
    }

    private Date date = new Date();
    public TicketEntity insertTicket(TicketDTO ticketDTO
                                            , SerialEntity serialEntity
                                            , WareHouseEntity wareHouseEntity
                                            , CategoryEntity categoryEntity
                                            , OrderEntity orderEntity
                                            , UserEntity userEntity) {
        // lấy DiscountAmount có kiểu dữ liệu BigDecimal để làm tròn
        BigDecimal value = new BigDecimal(String.valueOf(ticketDTO.getDiscountAmount()));

        TicketEntity ticket = new TicketEntity();
        ticket.setIdSerial(serialEntity);
        ticket.setIdWarehouse(wareHouseEntity);
        ticket.setIdCategory(categoryEntity);
        ticket.setIdOrder(orderEntity);
        ticket.setIdUser(userEntity);
        ticket.setStatus(1);
//        ticket.setExpiredTime(new Timestamp(date.getTime() + expiredTime));
        ticket.setDiscountType(ticketDTO.getDiscountType());
        // làm tròn số thập phân sau dấy phẩy thành 3 số
        ticket.setDiscountAmount(value.setScale(3, BigDecimal.ROUND_HALF_UP));
//        ticket.setBannerUrl("");
//        ticket.setThumbnailUrl("");
//        ticket.setAcquirerLogoUrl("");
        ticket.setTermOfUse(ticketDTO.getTermOfUse());
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setVoucherChannel(ticketDTO.getVoucherChannel());
//        ticket.setAvailbleFrom();
 //       ticket.setAvaibleTo(new Timestamp(date.getTime() + expiredTime));
        ticket.setAppliedStore(ticketDTO.getAppliedStore());

        return ticket;
        }
}
