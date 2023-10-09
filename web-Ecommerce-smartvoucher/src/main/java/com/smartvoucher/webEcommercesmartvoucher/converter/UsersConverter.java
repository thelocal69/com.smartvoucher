package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.UsersDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.UsersEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsersConverter {

    public List<UsersDTO> findAllUser(List<UsersEntity> list) {

        List<UsersDTO> listUser = new ArrayList<>();

        for (UsersEntity data : list) {
            UsersDTO usersDTO = new UsersDTO();
            usersDTO.setId(data.getId());
            usersDTO.setMemberCode(data.getMemberCode());
            usersDTO.setFirstName(data.getFirstName());
            usersDTO.setLastName(data.getLastName());
            usersDTO.setFullName(data.getFullName());
            usersDTO.setUserName(data.getUsername());
            usersDTO.setPhone(data.getPhone());
            usersDTO.setEmail(data.getEmail());
            usersDTO.setStatus(data.getStatus());
            usersDTO.setAddress(data.getAddress());
            usersDTO.setCreatedBy(data.getCreatedBy());
            usersDTO.setUpdatedBy(data.getUpdatedBy());
            usersDTO.setCreatedAt(data.getCreatedAt());
            usersDTO.setUpdatedAt(data.getUpdatedAt());

            listUser.add(usersDTO);
        }

        return listUser;
    }
}
