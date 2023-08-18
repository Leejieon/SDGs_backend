package com.SDGs.userservice.service;

import com.SDGs.userservice.dto.user.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto login(String email, String password);

    UserDto join(UserDto userDto);

    UserDto getUserDetailsByEmail(String email);

}
