package com.SDGs.userservice.dto.coordinator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoordinatorDto {

    private Long userId;

    private String field;

    private String strength;

}
