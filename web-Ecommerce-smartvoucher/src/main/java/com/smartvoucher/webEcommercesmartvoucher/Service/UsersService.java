package com.smartvoucher.webEcommercesmartvoucher.Service;

import com.smartvoucher.webEcommercesmartvoucher.Converter.UsersEntityToDTO;
import com.smartvoucher.webEcommercesmartvoucher.DTO.UsersDTO;
import com.smartvoucher.webEcommercesmartvoucher.Entity.UsersEntity;
import com.smartvoucher.webEcommercesmartvoucher.Repository.UsersRepository;
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
