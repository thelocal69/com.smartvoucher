package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.RoleUsersConverter;
import com.smartvoucher.webEcommercesmartvoucher.repository.IRoleUserRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.RoleRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
