package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SerialDTO {
    private long id;
    @NotNull(message = "Please fill all information!")
    @NotBlank(message = "Please fill all information!")
    private String batchCode;
    @NotNull(message = "Please fill all information!")
    private int numberOfSerial;
    @NotNull(message = "Please fill all information!")
    @NotBlank(message = "Please fill all information!")
    private String serialCode;
    @NotNull(message = "Please fill all information!")
    @Min(value = 1)
    @Max(value = 3)
    private int status;
    private String createdBy;
    private String updatedBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
