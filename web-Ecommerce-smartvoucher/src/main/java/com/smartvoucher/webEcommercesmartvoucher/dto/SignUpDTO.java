package com.smartvoucher.webEcommercesmartvoucher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Component
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {
    @NotNull(message = "Please fill all information!")
    @NotBlank(message = "Please fill all information!")
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9.-]+$", message = "Please enter the correct email format!" )
    private String email;
    @NotNull(message = "Please fill all information!")
    @NotBlank(message = "Please fill all information!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password is Minimum eight characters, at least one uppercase letter, one lowercase letter and one number" )
    private String password;
    @NotNull(message = "Please fill all information!")
    @NotBlank(message = "Please fill all information!")
    @Length(min = 10, max = 11, message = "The length of the phone number is at least 10 digits !")
    @Pattern(regexp = "0+(\\d*)", message = "Please enter the correct phone number format!")
    private String phone;
    private String roleName;
    private String token;
    private boolean isEnable;
    private Timestamp createdAt;
}
