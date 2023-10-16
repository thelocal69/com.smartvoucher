package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.*;
import com.smartvoucher.webEcommercesmartvoucher.dto.OrdersDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.TicketEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TicketConverter {

    public List<TicketDTO> findAllTicket(List<TicketEntity> list) {

        List<TicketDTO> listTicket = new ArrayList<>();

        for (TicketEntity data : list) {

            SerialDTO serialDTO = new SerialDTO();
            WareHouseDTO warehouseDTO = new WareHouseDTO();
            CategoryDTO categoryDTO = new CategoryDTO();
            OrdersDTO ordersDTO = new OrdersDTO();
            UsersDTO usersDTO = new UsersDTO();
            StoreDTO storeDTO = new StoreDTO();
            TicketDTO ticketDTO = new TicketDTO(); // This is ticketDTO

            serialDTO.setId(data.getIdSerial().getId());
            ticketDTO.setIdSerialDTO(serialDTO); // set id serial

            warehouseDTO.setId(data.getIdWarehouse().getId());
            ticketDTO.setIdWarehouseDTO(warehouseDTO); // set id warehouse

            categoryDTO.setId(data.getIdCategory().getId());
            ticketDTO.setIdCategoryDTO(categoryDTO); // set id category

            ordersDTO.setId(data.getIdOrder().getId());
            ticketDTO.setIdOrderDTO(ordersDTO); // set id order

            usersDTO.setId(data.getIdUser().getId());
            ticketDTO.setIdUserDTO(usersDTO); // set id User

            storeDTO.setId(data.getStore().getId());
            ticketDTO.setStoreDTO(storeDTO);

            ticketDTO.setId(data.getId());
            ticketDTO.setClaimedTime(data.getClaimedTime());
            ticketDTO.setRedeemedtimeTime(data.getRedeemedtimeTime());
            ticketDTO.setExpiredTime(data.getExpiredTime());
            ticketDTO.setDiscountType(data.getDiscountType());
            ticketDTO.setDiscountAmount(data.getDiscountAmount());
            ticketDTO.setBannerUrl(data.getBannerUrl());
            ticketDTO.setThumbnailUrl(data.getThumbnailUrl());
            ticketDTO.setAcquirerLogoUrl(data.getAcquirerLogoUrl());
            ticketDTO.setTermOfUse(data.getTermOfUse());
            ticketDTO.setDescription(data.getDescription());
            ticketDTO.setVoucherChannel(data.getVoucherChannel());
            ticketDTO.setAvailableFrom(data.getAvailbleFrom());
            ticketDTO.setAvailableTo(data.getAvaibleTo());
            ticketDTO.setAppliedStore(data.getStore().getStoreCode());

            // fill default status 1
            ticketDTO.setStatus(1);

            listTicket.add(ticketDTO);
        }

        return listTicket;

    }
}
