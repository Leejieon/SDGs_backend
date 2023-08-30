package com.learning_coordinator.matching.config.schedulers;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;


import com.learning_coordinator.matching.batch.SharedVectors;
import com.learning_coordinator.matching.domain.Role;
import com.learning_coordinator.matching.dto.CoordinatorResponseDTO;
import com.learning_coordinator.matching.dto.LearnerResponseDTO;
import com.learning_coordinator.matching.dto.UserServiceIdResponseDTO;
import com.learning_coordinator.matching.dto.UserServiceInformationResponseDTO;

import lombok.NoArgsConstructor;


// import static com.learning_coordinator.matching.config.schedulers.BatchScheduler.client;

@NoArgsConstructor
public class UserInformationItemReaderProcessor implements ItemProcessor<UserServiceIdResponseDTO, UserServiceInformationResponseDTO>{

    @Override
    @Nullable
    public UserServiceInformationResponseDTO process(@NonNull UserServiceIdResponseDTO item) throws Exception {
        UserServiceInformationResponseDTO userServiceInformationResponse = new UserServiceInformationResponseDTO(); 
        System.out.println("_________________PROCESSOR____"+SharedVectors.updateIdVector.toString()+ "_____");
        if(item.getRole() == Role.COORDINATOR){
            //CoordinatorResponseDTO coordinatorResponse = client.coordinatorInput(item.getId());
            CoordinatorResponseDTO coordinatorResponse = new CoordinatorResponseDTO((long) 124, "회화", "끈기");
            userServiceInformationResponse.setCoordinatorResponseDTO(coordinatorResponse);
            userServiceInformationResponse.setFlag(true);
            
        } else if(item.getRole() == Role.LEARNER){
            //LearnerResponseDTO learnerResponse = client.learnerInput(item.getId());
            LearnerResponseDTO learnerResponse = new LearnerResponseDTO((long) 123, "창의성", "게으름", "끈기 있는 코디네이터", "회화", 0);
            userServiceInformationResponse.setLearnerResponseDTO(learnerResponse);
            userServiceInformationResponse.setFlag(false);
        } else {
            return null;
        }
        return userServiceInformationResponse;
    }
}
