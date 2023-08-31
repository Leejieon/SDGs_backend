package com.SDGs.userservice.dto.learner;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LearnerResponse {
    private Long userId;

    private String strength;

    private String weakness;

    private String wish;

    private String purpose;

    private int attendances = 0;
}
