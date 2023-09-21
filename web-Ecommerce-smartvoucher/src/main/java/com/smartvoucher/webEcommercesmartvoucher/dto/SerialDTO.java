package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SerialDTO extends BaseDTO{
    private String batchCode;
    private int numberOfSerial;
    private String serialCode;
    private int status;
}
