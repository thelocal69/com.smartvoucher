package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDTO {
    private long id;
    private String avatarUrl;

    private String firstName;

    private String lastName;

    private String userName;

    private String fullName;
    @NotNull(message = "Please fill all information!")
    @NotBlank(message = "Please fill all information!")
    @Length(min = 10, max = 11, message = "The length of the phone number is at least 10 digits !")
    @Pattern(regexp = "0+(\\d*)", message = "Please enter the correct phone number format!")
    private String phone;

    private String email;

    private String address;
    private double balance;

    private Timestamp createdAt;

}
