package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.BuyTicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDetailDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.TicketEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TicketConverter {
    private final SerialConverter serialConverter;
    private final WareHouseConverter wareHouseConverter;
    private final CategoryConverter categoryConverter;
    private final OrderConverter orderConverter;
    private final UserConverter userConverter;

    private final StoreConverter storeConverter;


    @Autowired
    public TicketConverter(SerialConverter serialConverter
                    ,WareHouseConverter wareHouseConverter
                    ,CategoryConverter categoryConverter
                    ,OrderConverter orderConverter
                    ,UserConverter userConverter
                    ,StoreConverter storeConverter) {
            this.categoryConverter = categoryConverter;
            this.wareHouseConverter = wareHouseConverter;
            this.serialConverter = serialConverter;
            this.orderConverter = orderConverter;
            this.userConverter = userConverter;
            this.storeConverter = storeConverter;
    }

    public TicketDTO toTicketDTO(TicketEntity ticketEntity) {
            TicketDTO ticketDTO = new TicketDTO(); // This is ticketDTO
            ticketDTO.setIdSerialDTO(serialConverter.toSerialDTO(ticketEntity.getIdSerial())); // set id serial
            ticketDTO.setIdWarehouseDTO(wareHouseConverter.toWareHouseDTO(ticketEntity.getIdWarehouse()));
            ticketDTO.setIdOrderDTO(orderConverter.toOrderDTO(ticketEntity.getIdOrder()));// set id warehouse
            ticketDTO.setIdCategoryDTO(categoryConverter.toCategoryDTO(ticketEntity.getIdCategory())); // set id category
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
            ticketDTO.setIdStoreDTO(storeConverter.toStoreDTO(ticketEntity.getIdStore()));
            ticketDTO.setStatus(1);
        return ticketDTO;
    }

    public TicketDetailDTO toTicketDetailDTO(TicketEntity ticketEntity) {
        TicketDetailDTO ticketDetailDTO = new TicketDetailDTO(); // This is ticketDTO
        ticketDetailDTO.setSerialCode(ticketEntity.getIdSerial().getSerialCode()); // set id serial
        ticketDetailDTO.setWarehouseName(ticketEntity.getIdWarehouse().getName()); // set id warehouse
        ticketDetailDTO.setCategoryName(ticketEntity.getIdCategory().getName()); // set id category
        ticketDetailDTO.setOrderNo(ticketEntity.getIdOrder().getOrderNo()); // set id order
        ticketDetailDTO.setEmail(ticketEntity.getIdUser().getEmail()); // set id User
        ticketDetailDTO.setId(ticketEntity.getId());
        ticketDetailDTO.setClaimedTime(ticketEntity.getClaimedTime());
        ticketDetailDTO.setRedeemedtimeTime(ticketEntity.getRedeemedtimeTime());
        ticketDetailDTO.setExpiredTime(ticketEntity.getExpiredTime());
        ticketDetailDTO.setDiscountType(ticketEntity.getDiscountType());
        ticketDetailDTO.setDiscountAmount(ticketEntity.getDiscountAmount());
        ticketDetailDTO.setBannerUrl(ticketEntity.getBannerUrl());
        ticketDetailDTO.setThumbnailUrl(ticketEntity.getThumbnailUrl());
        ticketDetailDTO.setAcquirerLogoUrl(ticketEntity.getAcquirerLogoUrl());
        ticketDetailDTO.setTermOfUse(ticketEntity.getTermOfUse());
        ticketDetailDTO.setDescription(ticketEntity.getDescription());
        ticketDetailDTO.setVoucherChannel(ticketEntity.getVoucherChannel());
        ticketDetailDTO.setAvailableFrom(ticketEntity.getAvailbleFrom());
        ticketDetailDTO.setAvailableTo(ticketEntity.getAvaibleTo());
        ticketDetailDTO.setStoreName(ticketEntity.getIdStore().getName());
        ticketDetailDTO.setStatus(ticketEntity.getStatus());
        return ticketDetailDTO;
    }

   public List<TicketDetailDTO> listTicketDetailDTO(List<TicketEntity> ticketEntityList){
        return ticketEntityList.stream().map(this::toTicketDetailDTO).collect(Collectors.toList());
   }


    public TicketEntity updateTicket(int statusTicket, TicketEntity oldTicket) {
        if(!Objects.equals(statusTicket, oldTicket.getStatus())) {
            oldTicket.setStatus(statusTicket);
        }
        return oldTicket;
    }

    public TicketEntity toBuyTicketEntity(BuyTicketDTO buyTicketDTO, WareHouseEntity wareHouseEntity){
        TicketEntity ticketEntity = new TicketEntity();
        BigDecimal value = new BigDecimal(String.valueOf(buyTicketDTO.getDiscountAmount()));

        buyTicketDTO.setBannerUrl(wareHouseEntity.getBannerUrl());
        buyTicketDTO.setAvailableFrom(wareHouseEntity.getAvailableFrom());
        buyTicketDTO.setAvailableTo(wareHouseEntity.getAvailableTo());
        buyTicketDTO.setClaimedTime(new Timestamp(System.currentTimeMillis()));
        buyTicketDTO.setExpiredTime(wareHouseEntity.getAvailableTo());
        buyTicketDTO.setDiscountType(wareHouseEntity.getDiscountType().getName());
        buyTicketDTO.setDiscountAmount(value);

        ticketEntity.setStatus(buyTicketDTO.getStatus());
        ticketEntity.setBannerUrl(buyTicketDTO.getBannerUrl());
        ticketEntity.setClaimedTime(buyTicketDTO.getClaimedTime());
        ticketEntity.setAvailbleFrom(buyTicketDTO.getAvailableFrom());
        ticketEntity.setAvaibleTo(buyTicketDTO.getAvailableTo());
        ticketEntity.setExpiredTime(buyTicketDTO.getExpiredTime());
        ticketEntity.setDiscountType(buyTicketDTO.getDiscountType());
        ticketEntity.setDiscountAmount(buyTicketDTO.getDiscountAmount());
        return  ticketEntity;
    }
}
