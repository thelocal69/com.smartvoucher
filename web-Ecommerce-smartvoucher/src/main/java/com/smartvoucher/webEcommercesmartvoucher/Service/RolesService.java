package com.smartvoucher.webEcommercesmartvoucher.Service;

import com.smartvoucher.webEcommercesmartvoucher.Converter.RolesEntityToDTO;
import com.smartvoucher.webEcommercesmartvoucher.DTO.RolesDTO;
import com.smartvoucher.webEcommercesmartvoucher.Entity.RolesEntity;
import com.smartvoucher.webEcommercesmartvoucher.Repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesService {

    private final RolesRepository rolesRepository;

    @Autowired
    private RolesEntityToDTO rolesEntityToDTO;

    @Autowired
    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public List<RolesDTO> findAllRole() {

        List<RolesEntity> list = rolesRepository.findAll();

        List<RolesDTO> listRole = rolesEntityToDTO.findAllRole(list);

        return listRole;
    }


}
