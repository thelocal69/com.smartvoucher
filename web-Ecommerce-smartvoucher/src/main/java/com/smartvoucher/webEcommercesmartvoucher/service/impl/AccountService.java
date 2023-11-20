package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.gson.Gson;
import com.smartvoucher.webEcommercesmartvoucher.converter.RoleUsersConverter;
import com.smartvoucher.webEcommercesmartvoucher.converter.UserConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.ResetPasswordDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.RolesUsersDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.SignUpDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.token.RefreshTokenDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.RoleEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.RolesUsersEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.enums.Provider;
import com.smartvoucher.webEcommercesmartvoucher.entity.token.Tokens;
import com.smartvoucher.webEcommercesmartvoucher.entity.token.VerificationToken;
import com.smartvoucher.webEcommercesmartvoucher.exception.*;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseAuthentication;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseToken;
import com.smartvoucher.webEcommercesmartvoucher.repository.IRoleUserRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.RoleRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.token.ITokenRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.token.IVerificationTokenRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IAccountService;
import com.smartvoucher.webEcommercesmartvoucher.util.EmailUtil;
import com.smartvoucher.webEcommercesmartvoucher.util.JWTHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
@Slf4j
@Service
public class AccountService implements IAccountService {
    private final AuthenticationManager authenticationManager;
    private final JWTHelper jwtHelper;
    private final Gson gson;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final IRoleUserRepository roleUserRepository;
    private final ITokenRepository tokenRepository;
    private final IVerificationTokenRepository verificationTokenRepository;
    private final UserConverter userConverter;
    private final RoleUsersConverter roleUsersConverter;
    private final PasswordEncoder passwordEncoder;
    private final EmailUtil emailUtil;

    @Autowired
    public AccountService(final AuthenticationManager authenticationManager,
                          final JWTHelper jwtHelper,
                          final Gson gson,
                          final UserRepository userRepository,
                          final RoleRepository roleRepository,
                          final IRoleUserRepository roleUserRepository,
                          final ITokenRepository tokenRepository,
                          final IVerificationTokenRepository verificationTokenRepository,
                          final UserConverter userConverter,
                          final RoleUsersConverter roleUsersConverter,
                          final PasswordEncoder passwordEncoder,
                          final EmailUtil emailUtil
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
        this.gson = gson;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleUserRepository = roleUserRepository;
        this.tokenRepository = tokenRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.userConverter = userConverter;
        this.roleUsersConverter = roleUsersConverter;
        this.passwordEncoder = passwordEncoder;
        this.emailUtil = emailUtil;
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
        UserEntity user = userRepository.findByEmailAndProvider(email, Provider.local.name());
        revokeAllUserTokens(user);
        saveUserToken(user, refreshToken);
        log.info("Sign-in is successfully !");
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
            if (jwtHelper.validationToke(token)){
                final String data = jwtHelper.parserToken(token);
                if (data != null && !data.isEmpty()){
                    boolean isValidTokens = tokenRepository.findByToken(token)
                            .map(tokens ->
                                    !tokens.isExpired() && !tokens.isRevoke()
                            ).orElse(false);
                    if (isValidTokens){
                        RolesUsersEntity rolesUsers = roleUserRepository.findOneByEmailAndProvider(data, Provider.local.name());
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
                        log.info("Refresh token is expired or not exist !");
                        throw new TokenRefreshException(403,"Refresh token is expired or not exist !", null);
                    }
                }else {
                    log.info("Data is not exist !");
                    throw new JwtFilterException(403, "Data is not exist !", null);
                }
            }
        }
        log.info("Refresh token is completed !");
        return RefreshTokenDTO.builder()
                .accessToken(accessToken)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SignUpDTO SignUp(SignUpDTO signUpDTO) {
        if (userRepository.findByEmailAndProviderOrPhone(
                signUpDTO.getEmail(),
                Provider.local.name(),
                signUpDTO.getPhone()).isEmpty()) {
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
            log.info("Success!  Please, check your email for to complete your registration");
            return userConverter.signUp(userDTO);
        } else {
            log.info("Email or Phone is available! Please try again !");
            throw new UserAlreadyExistException(406, "Email or Phone is available! Please try again !");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserVerificationToken(UserEntity user, String token) {
        VerificationToken verificationToken = new VerificationToken(token, user);
        this.verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String verifyEmail(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken.getUser().isEnable()){
            log.info("This account has already been verified, please login!");
            return "This account has already been verified, please, login.";
        }
        String verificationResult = validateToken(token);
        if (verificationResult.equalsIgnoreCase("Valid")){
            log.info("Email verified successfully. Now you can login to your account");
            return "Email verified successfully. Now you can login to your account";
        }
        log.info("Invalid verification token !");
        return "Invalid verification token !";
    }

    @Override
    public String validateToken(String verifyToken) {
        VerificationToken token = verificationTokenRepository.findByToken(verifyToken);
        if(token == null){
            log.info("Invalid verification token !");
            throw new VerificationTokenException(500, "Invalid verification token !");
        }
        UserEntity userEntity = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            verificationTokenRepository.delete(token);
            log.info("Token already expired !");
            throw new VerificationTokenException(500, "Token already expired !");
        }
        userEntity.setEnable(true);
        userRepository.save(userEntity);
        log.info("Token valid");
        return "Valid";
    }

    @Override
    public String forgotPassword(String email) throws MessagingException, UnsupportedEncodingException {
        UserEntity user = userRepository.findByEmailAndProvider(email, Provider.local.name());
        if (user == null){
            log.info("User not exist !");
            throw new UserNotFoundException(404, "User not exist !");
        }
        this.emailUtil.sendResetPassword(email);
        log.info("Check your email to reset password if account was registered !");
        return "Check your email to reset password if account was register !";
    }

    @Override
    public String setPassword(ResetPasswordDTO resetPasswordDTO) {
        UserEntity user = userRepository.findByEmailAndProvider(resetPasswordDTO.getEmail(), Provider.local.name());
        if (user == null){
            log.info("User not exist !");
            throw new UserNotFoundException(404, "User not exist !");
        }
        user.setPwd(this.passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        this.userRepository.save(user);
        log.info("Set new password successfully !");
        return "Set new password successfully !";
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
