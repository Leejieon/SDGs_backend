package com.SDGs.userservice.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserJoinRequest {

    @NotNull(message = "Email cannot be null.")
    @Size(min = 2, message = "Email not be less than 2 characters.")
    private String email;

    @NotNull(message = "Name cannot be null.")
    @Size(min = 2, message = "Name not be less than 2 characters.")
    private String name;

    @NotNull(message = "Password cannot be null.")
    @Size(min = 8, message = "Password must be equal or greater than 8 characters.")
    private String password;

    private String phone;
}
