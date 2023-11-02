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

@Component
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {
    private String firstName;
    private String lastName;
    private String fullName;
    @NotNull(message = "Please fill all information!")
    @NotBlank(message = "Please fill all information!")
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@gmail.com", message = "Please enter the correct email format!" )
    private String email;
    private String address;
    @NotNull(message = "Please fill all information!")
    @NotBlank(message = "Please fill all information!")
    @Length(min = 8, message = "Minimum eight characters")
    private String userName;
    @NotNull(message = "Please fill all information!")
    @NotBlank(message = "Please fill all information!")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Minimum eight characters, at least one letter and one number" )
    private String password;
    @NotNull(message = "Please fill all information!")
    @NotBlank(message = "Please fill all information!")
    @Length(min = 10, max = 11, message = "The length of the phone number is at least 10 digits !")
    @Pattern(regexp = "0+(\\d*)", message = "Please enter the correct phone number format!")
    private String phone;
}
