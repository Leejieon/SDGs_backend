package com.learning_coordinator.matching.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoordinatorResponseDTO {
    private Long userId;
    private String field;
    private String strength;
}