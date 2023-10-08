package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.entityToDTO.UsersEntityToDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UsersDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.UsersEntity;
import com.smartvoucher.webEcommercesmartvoucher.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private UsersRepository usersRepository;

    @Autowired
    private UsersEntityToDTO usersEntityToDTO;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<UsersDTO> findAllUser() {

        List<UsersEntity> list = usersRepository.findAll();

        List<UsersDTO> listUser = usersEntityToDTO.findAllUser(list);

        return listUser;
    }
}
