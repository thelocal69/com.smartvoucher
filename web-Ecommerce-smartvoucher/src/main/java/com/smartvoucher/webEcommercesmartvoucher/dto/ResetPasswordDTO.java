package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDTO {
    private String email;
    private String newPassword;
}
