package com.SDGs.userservice.dto.coordinator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoordinatorRequest {
    private String field;

    private String strength;
}
