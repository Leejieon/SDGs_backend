package com.SDGs.userservice.service;

import com.SDGs.userservice.dto.user.UserDto;
import com.SDGs.userservice.domain.User;
import com.SDGs.userservice.exception.ErrorCode;
import com.SDGs.userservice.exception.UserServiceException;
import com.SDGs.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Environment env;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    @Override
    @Transactional
    public UserDto login(String email, String password) {

        User userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserServiceException(ErrorCode.USER_NOT_FOUND, String.format("%s is not founded.", email)));

        System.out.println(userEntity);
        // Check password
        if(!passwordEncoder.matches(password, userEntity.getEncryptedPwd())){
            throw new UserServiceException(ErrorCode.INVALID_PASSWORD);
        }

        ModelMapper mapper = new ModelMapper();
        UserDto userDto = mapper.map(userEntity, UserDto.class);

        System.out.println(userDto);
        return userDto;
    }

    @Override
    @Transactional
    public UserDto join(UserDto userDto) {
        // Check email is not duplicated
        userRepository.findByEmail(userDto.getEmail()).ifPresent(it -> {
            throw new UserServiceException(ErrorCode.DUPLICATED_USER_EMAIL, String.format("%s is duplicated.", userDto.getEmail()));
        });

        // mapping userDto to userEntity
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User userEntity = mapper.map(userDto, User.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPassword()));

        // save new user
        userRepository.save(userEntity);

        return mapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserServiceException(ErrorCode.USER_NOT_FOUND,
                        String.format("%s is not founded.", email)));

        return new ModelMapper().map(user, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserServiceException(ErrorCode.USER_NOT_FOUND,
                        String.format("%s is not founded.", email)));


        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>());
    }
}
