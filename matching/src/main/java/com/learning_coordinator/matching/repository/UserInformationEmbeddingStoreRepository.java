package com.learning_coordinator.matching.repository;

import java.util.Date;

import com.learning_coordinator.matching.domain.UserInformationEmbedding;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

public interface UserInformationEmbeddingStoreRepository{
    public void updateEmbeddingStoreObject(UserInformationEmbedding update);
    public InMemoryEmbeddingStore<UserInformationEmbedding> getEmbeddingStore();
    public void setLastBatchDate(Date lastBatchDate);
}
