package com.smartvoucher.webEcommercesmartvoucher.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {
    private int statusCode;
    private String message;
    private Object data;
}
