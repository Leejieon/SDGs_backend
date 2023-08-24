package com.SDGs.userservice.dto.user;

import com.SDGs.userservice.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String email;
    private String name;
    private String password;
    private String phone;
    private Role role;
    private String encryptedPwd;
    private String userId;

    private boolean matching;
}
