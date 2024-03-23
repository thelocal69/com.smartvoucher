package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.*;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface IUserService {
    String uploadUserImages(MultipartFile fileName, Principal connectedUser);
    List<UserDTO> getAllUser();
    ResponseOutput getAllUser(int page, int limit, String sortBy, String sortField);
    List<UserDTO> searchUserByEmail(String email);
    UserDTO getEmail(UserDTO userDTO);
    UserDetailDTO getUserById(Long id);
    String changePassword(ChangePasswordDTO changePasswordDTO, Principal connectedUser);
    UserDetailDTO getInformationLoginUser(Principal connectedUser);
    String editUserProfile(UserDetailDTO userDetailDTO, Principal connectedUser);
    Boolean blockUser(BlockUserDTO blockUserDTO);
    BuyVoucherDTO buyTicketByBalance(BuyVoucherDTO buyVoucherDTO, Principal connectedUser);
}
