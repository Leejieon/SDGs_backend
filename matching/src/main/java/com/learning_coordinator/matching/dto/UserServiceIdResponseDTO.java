package com.learning_coordinator.matching.dto;

import com.learning_coordinator.matching.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserServiceIdResponseDTO {
    private Role role;
    private Long id;
}
