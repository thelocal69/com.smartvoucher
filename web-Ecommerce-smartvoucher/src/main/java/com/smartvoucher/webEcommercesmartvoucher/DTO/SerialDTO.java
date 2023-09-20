package com.smartvoucher.webEcommercesmartvoucher.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SerialDTO {

    private long id;

    private String batchCode;

    private int numberOfSerial;

    private String serialCode;

    private int status;

    private String createdBy;

    private String updatedBy;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
