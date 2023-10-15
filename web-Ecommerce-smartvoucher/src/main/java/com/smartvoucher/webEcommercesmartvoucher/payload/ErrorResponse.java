package com.smartvoucher.webEcommercesmartvoucher.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private Timestamp timestamp;
    private int status;
    private String error;
    private String message; // error message details
    private String path; // path : place of error
}
