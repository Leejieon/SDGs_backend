package com.learning_coordinator.matching.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class LearnerInformationDTO {
    @NonNull
    private Long learnerId;
    @NonNull
    private Long userId;

    private String strength;
    private String weakness;
    private String wish;
    private String purpose;
    private int attendances;

    @Override
    public String toString() {
        return "학습자의 강점 : " + strength + "\n학습자의 약점 : " + weakness + "\n학습자가 바라는 점 : " + purpose;
    }
}
