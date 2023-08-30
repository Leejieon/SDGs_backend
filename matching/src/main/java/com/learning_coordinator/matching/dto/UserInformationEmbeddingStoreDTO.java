package com.learning_coordinator.matching.dto;

import java.util.Date;

import com.learning_coordinator.matching.domain.UserInformationEmbedding;

import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInformationEmbeddingStoreDTO {
    private Long id;
    private Date lastbatchdate;
    private EmbeddingStore<UserInformationEmbedding> embeddingStore;
}
