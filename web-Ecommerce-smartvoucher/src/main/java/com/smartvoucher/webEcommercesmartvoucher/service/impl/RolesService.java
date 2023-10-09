package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.RolesConverter;
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
    private RolesConverter rolesConverter;

    @Autowired
    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public List<RolesDTO> findAllRole() {

        List<RolesEntity> list = rolesRepository.findAll();

        List<RolesDTO> listRole = rolesConverter.findAllRole(list);

        return listRole;
    }


}
