package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

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
    private String serialCode;
    @NotNull(message = "Please fill all information!")
    @Min(value = 0)
    @Max(value = 2)
    private int status;
    private String createdBy;
    private String updatedBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
