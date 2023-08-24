package com.SDGs.userservice.controller;

import com.SDGs.userservice.dto.coordinator.CoordinatorDto;
import com.SDGs.userservice.dto.coordinator.CoordinatorRequest;
import com.SDGs.userservice.dto.coordinator.CoordinatorResponse;
import com.SDGs.userservice.dto.learner.LearnerDto;
import com.SDGs.userservice.dto.learner.LearnerRequest;
import com.SDGs.userservice.dto.learner.LearnerResponse;
import com.SDGs.userservice.dto.user.UserDto;
import com.SDGs.userservice.domain.Role;
import com.SDGs.userservice.service.CoordinatorService;
import com.SDGs.userservice.service.LearnerService;
import com.SDGs.userservice.service.UserService;
import com.SDGs.userservice.dto.user.UserJoinRequest;
import com.SDGs.userservice.dto.user.UserLoginRequest;
import com.SDGs.userservice.dto.user.UserJoinResponse;
import com.SDGs.userservice.dto.user.UserLoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/user-service")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final LearnerService learnerService;
    private final CoordinatorService coordinatorService;

    private final ModelMapper mapper;

    @PostMapping("learner/join")
    public ResponseEntity<UserJoinResponse> learnerJoin(@RequestBody UserJoinRequest request) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(request, UserDto.class);
        userDto.setRole(Role.LEARNER);
        userService.join(userDto);

        UserJoinResponse response = mapper.map(userDto, UserJoinResponse.class);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("coordinator/join")
    public ResponseEntity<UserJoinResponse> coordinatorJoin(@RequestBody UserJoinRequest request) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(request, UserDto.class);
        userDto.setRole(Role.COORDINATOR);
        userService.join(userDto);

        UserJoinResponse response = mapper.map(userDto, UserJoinResponse.class);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        UserDto userDto = userService.login(request.getEmail(), request.getPassword());

        UserLoginResponse response = mapper.map(userDto, UserLoginResponse.class);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/learner/{userId}")
    public ResponseEntity<LearnerResponse> learnerInput(@PathVariable(name = "userId") Long userId,
                                                        @RequestBody LearnerRequest request) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        LearnerDto learnerDto = mapper.map(request, LearnerDto.class);
        // userId와 learner의 데이터를 userId를 통해 연결
        learnerDto.setUserId(userId);
        learnerDto.setAttendances(0);

        learnerService.register(learnerDto);

        LearnerResponse response = mapper.map(learnerDto, LearnerResponse.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/coordinator/{userId}")
    public ResponseEntity<CoordinatorResponse> coordinatorInput(@PathVariable(name = "userId") Long userId,
                                                            @RequestBody CoordinatorRequest request) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        CoordinatorDto coordinatorDto = mapper.map(request, CoordinatorDto.class);
        // userId와 learner의 데이터를 userId를 통해 연결
        coordinatorDto.setUserId(userId);

        coordinatorService.register(coordinatorDto);

        CoordinatorResponse response = mapper.map(coordinatorDto, CoordinatorResponse.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }



}
