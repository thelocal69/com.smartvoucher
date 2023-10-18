package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.RoleConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.RoleDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.RoleEntity;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.RoleRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    @Autowired
    private RoleConverter roleConverter;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseObject getAllRole() throws Exception {

        List<RoleDTO> listRole;

        try {

            List<RoleEntity> list = roleRepository.findAll();
            listRole = roleConverter.findAllRole(list);

        } catch (Exception e) {
            System.out.println("Ticket Service : " + e.getLocalizedMessage());
            return new ResponseObject(500, e.getLocalizedMessage(), "Not found List Roles !");
        }

        return new ResponseObject(200, "List Roles", listRole );
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseObject insertRole(RoleDTO roleDTO) throws Exception {

        boolean isSuccess = false;
        int status = 501;

        if (!roleDTO.getName().startsWith("ROLE_")) {
            String roleName = "ROLE_" + roleDTO.getName();
            roleDTO.setName(roleName);
        }

        Optional<RoleEntity> role = roleRepository.findByName(roleDTO.getName());

        if (role.isEmpty()) {

            try {

                roleRepository.save(roleConverter.insertRole(roleDTO));
                isSuccess = true;
                status = 200;

            } catch (javax.validation.ConstraintViolationException ex) {
                throw new javax.validation.ConstraintViolationException("Validation Fail!", ex.getConstraintViolations());

            } catch (Exception e) {
                System.out.println("Role service : " + e.getLocalizedMessage() );
                return new ResponseObject(500, e.getLocalizedMessage(), isSuccess);
            }
        }

        String message = (isSuccess == true) ? "Add Role success!":"Role is available, add role fail!";

        return new ResponseObject(status, message, isSuccess);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseObject updateRole(RoleDTO roleDTO) throws Exception {

        boolean isSuccess = false;
        int status = 501;

        if (!roleDTO.getName().startsWith("ROLE_")) {
            String roleName = "ROLE_" + roleDTO.getName();
            roleDTO.setName(roleName);
        }

        Optional<RoleEntity> oldRole = roleRepository.findById(roleDTO.getId());
        List<RoleEntity> checkRole = roleRepository.findByNameAndId(roleDTO.getName(), roleDTO.getId());

            if (!oldRole.isEmpty() && checkRole.isEmpty()) {
                try {
                    roleRepository.save(roleConverter.updateRole(roleDTO, oldRole.orElse(null)));
                    isSuccess = true;
                    status = 200;

                } catch (javax.validation.ConstraintViolationException ex) {
                    throw new javax.validation.ConstraintViolationException("Validation Fail!", ex.getConstraintViolations());

                } catch (Exception e) {
                    System.out.println("Role Service : " + e.getLocalizedMessage());
                    return new ResponseObject(500, e.getLocalizedMessage(), false);
                }
            }
        String message = (isSuccess == true) ? "Update Role Success!": "update Role fail!";

        return new ResponseObject(status, message, isSuccess);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseObject deleteRole(long id) throws Exception{

        boolean checkRole = roleRepository.existsById(id);
        int status = 501;

        if(checkRole == true) {
            try {

                roleRepository.deleteById(id);
                status = 200;

            } catch (Exception e) {
                System.out.println("Role Service : " + e.getLocalizedMessage());
                return new ResponseObject(500 , e.getLocalizedMessage() ,false);
            }
        }
        String message = (checkRole == true) ? "Delete Role Success!": "Role not Available, Delete Role Fail!";

        return new ResponseObject(status, message, checkRole);
    }


}
