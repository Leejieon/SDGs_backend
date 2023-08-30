package com.learning_coordinator.matching.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchingInformationDTO {
    private Long matchingId;
    private Long coordinatorId;
    private Long learnerId;

    public MatchingInformationDTO(Long coordinatorId, Long learnerId){
        this.coordinatorId = coordinatorId;
        this.learnerId = learnerId; 
    }
}
