package com.smartvoucher.webEcommercesmartvoucher.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckStatusWarehouseException extends RuntimeException {
    private int statusCode;
    private String message;
}
