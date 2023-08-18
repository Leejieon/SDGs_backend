package com.SDGs.userservice.dto.user;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginRequest {

    @NotNull(message = "Email cannot be null.")
    @Size(min = 2, message = "Email not be less than 2 characters.")
    private String email;

    @NotNull(message = "Password cannot be null.")
    @Size(min = 8, message = "Password must be equal or greater than 8 characters.")
    private String password;
}
