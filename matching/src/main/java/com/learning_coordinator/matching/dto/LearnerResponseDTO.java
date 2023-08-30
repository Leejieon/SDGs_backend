package com.learning_coordinator.matching.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LearnerResponseDTO {
    private Long userId;

    private String strength;

    private String weakness;

    private String wish;

    private String purpose;

    private int attendances = 0;
}
