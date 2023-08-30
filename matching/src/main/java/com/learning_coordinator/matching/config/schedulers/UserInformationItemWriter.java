package com.learning_coordinator.matching.config.schedulers;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.lang.NonNull;

import com.learning_coordinator.matching.batch.SharedVectors;
import com.learning_coordinator.matching.domain.Role;
import com.learning_coordinator.matching.domain.UserInformationEmbedding;
import com.learning_coordinator.matching.repository.Implementations.UserInformationEmbeddingStoreRepositoryImpl_JSON;

public class UserInformationItemWriter implements ItemWriter<UserInformationEmbedding> {
    private UserInformationEmbeddingStoreRepositoryImpl_JSON userInformationEmbeddingStoreRepo;

    public UserInformationItemWriter (UserInformationEmbeddingStoreRepositoryImpl_JSON userInformationEmbeddingStoreRepo){
        this.userInformationEmbeddingStoreRepo = userInformationEmbeddingStoreRepo;
    }

    @Override
    public void write(@NonNull Chunk<? extends UserInformationEmbedding> chunk) {
        for (UserInformationEmbedding userInformationEmbedding : chunk) {
            System.out.println("________________________User Response Writer_____________________");
            System.out.println(userInformationEmbedding.getRole() + " ID : " + userInformationEmbedding.getUserId().toString());
            System.out.println(userInformationEmbedding.getEmbedding().vectorAsList().subList(0, 3) + " ... " + userInformationEmbedding.getEmbedding().vectorAsList().subList(Math.max(userInformationEmbedding.getEmbedding().vectorAsList().size() - 4, 0), userInformationEmbedding.getEmbedding().vectorAsList().size()));
            if(userInformationEmbedding.getRole() == Role.COORDINATOR){
                userInformationEmbeddingStoreRepo.updateEmbeddingStoreObject(userInformationEmbedding);
            } else if(userInformationEmbedding.getRole() == Role.LEARNER) {
                SharedVectors.updateLearnerInformationEmbeddingVector.add(userInformationEmbedding);
            }
        }
    }
}
