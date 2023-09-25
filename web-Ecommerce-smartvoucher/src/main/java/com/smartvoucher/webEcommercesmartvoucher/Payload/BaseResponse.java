package com.smartvoucher.webEcommercesmartvoucher.Payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class BaseResponse {

    private String status;

    private String message;

    private Object data;
}
