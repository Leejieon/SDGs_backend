package com.learning_coordinator.matching.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class CoordinatorInformationDTO {
    @NonNull
    private Long coordinatorId;
    @NonNull
    private Long userId;
    
    private String field;
    private String strength;

    @Override
    public String toString(){
        return "분야 : " + field + "\n강점 : " + strength;
    }
}
