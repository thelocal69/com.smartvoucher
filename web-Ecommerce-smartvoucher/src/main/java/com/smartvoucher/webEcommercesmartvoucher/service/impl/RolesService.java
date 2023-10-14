package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.RolesConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.RolesDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.RolesEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.TicketEntity;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.RolesRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RolesService implements IRoleService {

    private final RolesRepository rolesRepository;

    @Autowired
    private RolesConverter rolesConverter;

    @Autowired
    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseObject getAllRole() {

        List<RolesDTO> listRole;

        try {

            List<RolesEntity> list = rolesRepository.findAll();
            listRole = rolesConverter.findAllRole(list);

        } catch (Exception e) {
            System.out.println("Ticket Service : " + e.getLocalizedMessage());
            return new ResponseObject(500, e.getLocalizedMessage(), "Not found List Roles !");
        }

        return new ResponseObject(200, "List Roles", listRole );
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseObject insertRole(RolesDTO rolesDTO) {

        boolean isSuccess = false;
        int status = 501;

        if (!rolesDTO.getName().startsWith("ROLE_")) {
            String roleName = "ROLE_" + rolesDTO.getName();
            rolesDTO.setName(roleName);
        }

        Optional<RolesEntity> role = rolesRepository.findByName(rolesDTO.getName());

        if (role.isEmpty()) {

            try {

                rolesRepository.save(rolesConverter.insertRole(rolesDTO));
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
    public ResponseObject updateRole(RolesDTO rolesDTO) {

        boolean isSuccess = false;
        int status = 501;

        if (!rolesDTO.getName().startsWith("ROLE_")) {
            String roleName = "ROLE_" + rolesDTO.getName();
            rolesDTO.setName(roleName);
        }

        Optional<RolesEntity> oldRole = rolesRepository.findById(rolesDTO.getId());
        List<RolesEntity> checkRole = rolesRepository.findByNameAndId(rolesDTO.getName(), rolesDTO.getId());

            if (!oldRole.isEmpty() && checkRole.isEmpty()) {
                try {
                    rolesRepository.save(rolesConverter.updateRole(rolesDTO, oldRole.orElse(null)));
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
    public ResponseObject deleteRole(long id) {

        boolean checkRole = rolesRepository.existsById(id);
        int status = 501;

        if(checkRole == true) {
            try {

                rolesRepository.deleteById(id);
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
