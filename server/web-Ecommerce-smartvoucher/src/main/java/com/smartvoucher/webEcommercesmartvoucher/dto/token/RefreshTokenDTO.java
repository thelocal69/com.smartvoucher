package com.smartvoucher.webEcommercesmartvoucher.dto.token;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDTO {
    @NotBlank
    private String accessToken;
}
