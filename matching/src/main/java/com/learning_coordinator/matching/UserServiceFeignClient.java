package com.learning_coordinator.matching;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import com.learning_coordinator.matching.dto.CoordinatorResponseDTO;
import com.learning_coordinator.matching.dto.LearnerResponseDTO;
import com.learning_coordinator.matching.dto.UserServiceIdResponseDTO;

//@FeignClient(name = "user-service")
public interface UserServiceFeignClient {
    @RequestMapping("/{a}")
    int getNumberOfUpdate(Date lastbatchDate);
    @RequestMapping("/{{b}}")
    List<UserServiceIdResponseDTO> getUpdateUserInformation(Date lastbatchDate);
    LearnerResponseDTO learnerInput(Long id);
    CoordinatorResponseDTO coordinatorInput(Long id);
      
}
