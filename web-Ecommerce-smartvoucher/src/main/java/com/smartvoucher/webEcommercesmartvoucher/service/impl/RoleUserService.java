package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.RoleUsersConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.RolesUsersDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.RoleEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.RolesUsersEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.keys.RolesUsersKeys;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.IRoleUserRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.RoleRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IRoleUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class RoleUserService implements IRoleUserService {

    private final IRoleUserRepository roleUserRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleUsersConverter roleUsersConverter;

    @Autowired
    public RoleUserService(final IRoleUserRepository roleUserRepository,
                           final RoleUsersConverter roleUsersConverter,
                           final UserRepository userRepository,
                           final RoleRepository roleRepository
    ) {
        this.roleUserRepository = roleUserRepository;
        this.roleUsersConverter = roleUsersConverter;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolesUsersDTO> getAllRoleUser() {
        List<RolesUsersEntity> rolesUsersEntityList = roleUserRepository.findAll();
        return roleUsersConverter.toRoleUserDTOList(rolesUsersEntityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RolesUsersDTO insert(RolesUsersDTO rolesUsersDTO) {
        RolesUsersEntity rolesUsersEntity = new RolesUsersEntity();
            if(rolesUsersDTO.getKeys()!= null){
            if(roleRepository.findAllByName(rolesUsersDTO.getRoleName()).isEmpty()
                    || userRepository.findAllByMemberCode(rolesUsersDTO.getMemberCode()).isEmpty()){
                log.info("Role code is not exist or user code is not exist!");
                throw new ObjectNotFoundException(
                        406,"Role code is not exist or user code is not exist!"
                );
            }
            else if(roleRepository.findAllByName(rolesUsersDTO.getRoleName()).isEmpty()
                    && userRepository.findAllByMemberCode(rolesUsersDTO.getMemberCode()).isEmpty()){
                log.info("Role code and user code is not exist");
                throw new ObjectNotFoundException(
                        406, "Role code and user code is not exist"
                );
            }
        }else{
                RoleEntity role = roleRepository.findOneByName(rolesUsersDTO.getRoleName());
                UserEntity user = userRepository.findOneByMemberCode(rolesUsersDTO.getMemberCode());
                rolesUsersEntity.setIdRole(role);
                rolesUsersEntity.setIdUser(user);
            //set keys
                RolesUsersKeys keys = new RolesUsersKeys();
                keys.setIdRole(role.getId());
                keys.setIdUser(user.getId());
                rolesUsersEntity.setRoleUserKeys(keys);
                log.info("Insert roleUser completed !");
        }
        return roleUsersConverter.toRoleUserDTO(roleUserRepository.save(rolesUsersEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(RolesUsersDTO rolesUsersDTO) {
        RolesUsersEntity rolesUsersEntity = roleUserRepository.findByIdRoleAndIdUser(
                rolesUsersDTO.getIdRole(), rolesUsersDTO.getIdUser()
        );
        if (rolesUsersEntity == null){
            log.info("Cannot delete roleUser because id is null !");
            throw new ObjectNotFoundException(406, "Cannot delete id = null !");
        }
        log.info("Delete roleUser is completed !");
        this.roleUserRepository.deleteByRoleUserKeys(rolesUsersEntity.getRoleUserKeys());
    }
}
