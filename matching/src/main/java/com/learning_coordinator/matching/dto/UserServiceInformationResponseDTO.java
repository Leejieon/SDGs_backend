package com.learning_coordinator.matching.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserServiceInformationResponseDTO {
    /*
     * flag 
     *  if sets true -> store CoordinatorResponseDTO
     *  if sets false -> store LearnerResponseDTO
     */
    private CoordinatorResponseDTO coordinatorResponse;
    private LearnerResponseDTO learnerResponse;
    private Boolean flag;

    public void setCoordinatorResponseDTO(CoordinatorResponseDTO coordinatorResponse){
        this.coordinatorResponse = coordinatorResponse;
        this.flag = true;
    }

    public void setLearnerResponseDTO(LearnerResponseDTO learnerResponse){
        this.learnerResponse = learnerResponse;
        this.flag = false;
    }
}
