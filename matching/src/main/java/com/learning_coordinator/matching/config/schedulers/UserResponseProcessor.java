package com.learning_coordinator.matching.config.schedulers;

import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.learning_coordinator.matching.domain.UserInformationEmbedding;
import com.learning_coordinator.matching.dto.CoordinatorResponseDTO;
import com.learning_coordinator.matching.dto.LearnerResponseDTO;
import com.learning_coordinator.matching.dto.UserServiceInformationResponseDTO;

import static com.learning_coordinator.matching.utility.EmbeddingModel.makeUser_InformationEmbeddingDTO;

public class UserResponseProcessor extends ItemListenerSupport<UserServiceInformationResponseDTO, UserInformationEmbedding> implements ItemProcessor<UserServiceInformationResponseDTO, UserInformationEmbedding> {
    @Override
    @Nullable
    public UserInformationEmbedding process(@NonNull UserServiceInformationResponseDTO item) throws Exception {
        System.out.println("________________________UserResponse Processor______________________");
        if(item.getFlag()){
            CoordinatorResponseDTO coordinatorInfo = item.getCoordinatorResponse();
            UserInformationEmbedding coordinatorInformationEmbedding = makeUser_InformationEmbeddingDTO(coordinatorInfo);
            return coordinatorInformationEmbedding;
        } else {
            LearnerResponseDTO learnerInfo = item.getLearnerResponse();
            UserInformationEmbedding learnerInformationEmbedding = makeUser_InformationEmbeddingDTO(learnerInfo);
            return learnerInformationEmbedding;   
        }
    }
}
