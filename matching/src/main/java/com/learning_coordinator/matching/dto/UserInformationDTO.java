package com.learning_coordinator.matching.dto;

import java.util.Date;

import com.learning_coordinator.matching.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class UserInformationDTO {
    @NonNull
    private Long id;
    
    @NonNull
    private Role role;

    private String name;
    private String email;
    private boolean matching;
    private Date createdDate;
    private Date modifiedDate;
}