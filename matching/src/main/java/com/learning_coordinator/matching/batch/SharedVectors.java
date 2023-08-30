package com.learning_coordinator.matching.batch;

import java.util.Vector;

import com.learning_coordinator.matching.domain.UserInformationEmbedding;
import com.learning_coordinator.matching.dto.UserServiceIdResponseDTO;
import com.learning_coordinator.matching.dto.UserServiceInformationResponseDTO;

import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

public class SharedVectors {
    public static Vector<UserServiceIdResponseDTO> updateIdVector = new Vector<>();
    public static Vector<UserServiceInformationResponseDTO> updateInformationVector = new Vector<>();
    public static Vector<UserInformationEmbedding> updateLearnerInformationEmbeddingVector = new Vector<>();
    public static InMemoryEmbeddingStore<UserInformationEmbedding> updateCoordinatorInformationEmbeddingStore = new InMemoryEmbeddingStore<>();
    public static void init() {
        updateIdVector = new Vector<>();
        updateInformationVector = new Vector<>();
        updateLearnerInformationEmbeddingVector = new Vector<>();
    }
}
