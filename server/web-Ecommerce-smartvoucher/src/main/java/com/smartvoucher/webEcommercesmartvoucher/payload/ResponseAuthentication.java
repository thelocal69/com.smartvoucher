package com.smartvoucher.webEcommercesmartvoucher.payload;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAuthentication {
    private String accessToken;
    private String refreshToken;
}
