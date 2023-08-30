package com.learning_coordinator.matching.config.schedulers;

import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.learning_coordinator.matching.domain.MatchingInformation;
import com.learning_coordinator.matching.domain.UserInformationEmbedding;
import com.learning_coordinator.matching.dto.MatchingInformationDTO;
import com.learning_coordinator.matching.repository.Implementations.UserInformationEmbeddingStoreRepositoryImpl_JSON;
import com.learning_coordinator.matching.service.Matching;

public class MatchingProcessor extends ItemListenerSupport<UserInformationEmbedding, MatchingInformation> implements ItemProcessor<UserInformationEmbedding, MatchingInformation> {
    private UserInformationEmbeddingStoreRepositoryImpl_JSON userInformationEmbeddingStoreRepo;
    public MatchingProcessor(UserInformationEmbeddingStoreRepositoryImpl_JSON userInformationEmbeddingStoreRepo){
        this.userInformationEmbeddingStoreRepo = userInformationEmbeddingStoreRepo;
    }
    @Override
    @Nullable
    public MatchingInformation process(@NonNull UserInformationEmbedding item) throws Exception {
        System.out.println("___________________________Matching Process____________________________");
        Matching matchingservice = new Matching(userInformationEmbeddingStoreRepo);
        MatchingInformationDTO matchingInformationDTO = matchingservice.matchingTarget(item);
        return MatchingInformation.builder()
            .CoordinatorId(matchingInformationDTO.getCoordinatorId())
            .LearnerId(matchingInformationDTO.getLearnerId())
            .build();
        }
}