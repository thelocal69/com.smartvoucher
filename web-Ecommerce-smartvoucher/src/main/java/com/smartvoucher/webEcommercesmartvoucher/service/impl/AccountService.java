package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.gson.Gson;
import com.smartvoucher.webEcommercesmartvoucher.converter.RoleUsersConverter;
import com.smartvoucher.webEcommercesmartvoucher.converter.UserConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.RolesUsersDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.SignUpDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.token.RefreshTokenDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.RoleEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.RolesUsersEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.token.Tokens;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.JwtFilterException;
import com.smartvoucher.webEcommercesmartvoucher.exception.TokenRefreshException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseAuthentication;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseToken;
import com.smartvoucher.webEcommercesmartvoucher.repository.IRoleUserRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.RoleRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.token.ITokenRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IAccountService;
import com.smartvoucher.webEcommercesmartvoucher.util.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService implements IAccountService {

    private final AuthenticationManager authenticationManager;
    private final JWTHelper jwtHelper;
    private final Gson gson;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final IRoleUserRepository roleUserRepository;
    private final ITokenRepository tokenRepository;
    private final UserConverter userConverter;
    private final RoleUsersConverter roleUsersConverter;

    @Autowired
    public AccountService(final AuthenticationManager authenticationManager,
                          final JWTHelper jwtHelper,
                          final Gson gson,
                          final UserRepository userRepository,
                          final RoleRepository roleRepository,
                          final IRoleUserRepository roleUserRepository,
                          final ITokenRepository tokenRepository,
                          final UserConverter userConverter,
                          final RoleUsersConverter roleUsersConverter
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
        this.gson = gson;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleUserRepository = roleUserRepository;
        this.tokenRepository = tokenRepository;
        this.userConverter = userConverter;
        this.roleUsersConverter = roleUsersConverter;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseAuthentication token(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                email, password
        );
        this.authenticationManager.authenticate(authenticationToken);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<SimpleGrantedAuthority> roles = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        ResponseToken data = new ResponseToken();
        data.setUsername(email);
        data.setRoles(roles);
        String token = jwtHelper.generateToken(gson.toJson(data));
        String refreshToken = jwtHelper.generateRefreshToken(email);
        UserEntity user = userRepository.findOneByEmail(email);
        revokeAllUserTokens(user);
        saveUserToken(user, refreshToken);
        return ResponseAuthentication.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public RefreshTokenDTO refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String headerValue = request.getHeader("Authorization");
        String accessToken = "";
        if (headerValue != null && headerValue.startsWith("Bearer ")){
            final String token = headerValue.substring(7);
            final String data = jwtHelper.parserToken(token);
            if (data != null && !data.isEmpty()){
                boolean isValidTokens = tokenRepository.findByToken(token)
                        .map(tokens ->
                                !tokens.isExpired() && !tokens.isRevoke()
                        ).orElse(false);
                if (isValidTokens){
                    RolesUsersEntity rolesUsers = roleUserRepository.getEmail(data);
                    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(
                            rolesUsers.getIdRole().getName()
                    );
                    List<SimpleGrantedAuthority> roles = new ArrayList<>();
                    roles.add(simpleGrantedAuthority);
                    ResponseToken resource = new ResponseToken();
                    resource.setUsername(rolesUsers.getIdUser().getEmail());
                    resource.setRoles(roles);
                    accessToken = jwtHelper.generateToken(gson.toJson(resource));
                }else {
                    throw new TokenRefreshException(403,"Refresh token is expired or not exist !", null);
                }
            }else {
                throw new JwtFilterException(403, "Data is not exist !", null);
            }
        }
        return RefreshTokenDTO.builder()
                .accessToken(accessToken)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject SignUp(SignUpDTO signUpDTO) {
        if (userRepository.findByEmailOrPhone(
                signUpDTO.getEmail(),
                signUpDTO.getPhone()).isEmpty()) {
            if(userRepository.findByUsername(signUpDTO.getUserName()) == null) {
                //save user trước
                UserDTO userDTO = userConverter.toUserDTO(
                        userRepository.save(userConverter.signUp(signUpDTO))
                );
                //get id role ở phần controller
                RoleEntity role = roleRepository.findOneByName(signUpDTO.getRoleName());
                //get id user mới thêm lúc nãy
                UserEntity user = userRepository.findOneByMemberCode(userDTO.getMemberCode());
                //biến đổi nó thành dto đê xử lí
                RolesUsersDTO rolesUsersDTO = roleUsersConverter.toRoleUserDTO(user, role);
                //save cái dto converter đã xử lý xuống database
                this.roleUserRepository.save(
                        roleUsersConverter.toRoleUserEntity(rolesUsersDTO)
                );
                return new ResponseObject(
                        200,
                        "SignUp success!",
                        userDTO);
            } else {
                throw new DuplicationCodeException(400, "UserName is available! please try again.");
            }
        } else {
            throw new DuplicationCodeException(400, "Email or Phone is available! please try again.");
        }
    }

    private  void saveUserToken(UserEntity user, String jwtToken){
        Tokens tokens = Tokens.builder()
                .user(user)
                .token(jwtToken)
                .tokenType("Bearer")
                .expired(false)
                .revoke(false)
                .build();
        this.tokenRepository.save(tokens);
    }

    private void revokeAllUserTokens(UserEntity user){
        List<Tokens> validUserTokens = tokenRepository.findAllTokenValidByUser(user.getId());
        if (!validUserTokens.isEmpty()){
            validUserTokens.forEach(tokens -> {
                tokens.setExpired(true);
                tokens.setRevoke(true);
            });
        }
        this.tokenRepository.saveAll(validUserTokens);
    }
}
