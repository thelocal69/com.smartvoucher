package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.entityToDTO.RolesEntityToDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.RolesDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.RolesEntity;
import com.smartvoucher.webEcommercesmartvoucher.repository.RolesRepository;
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
