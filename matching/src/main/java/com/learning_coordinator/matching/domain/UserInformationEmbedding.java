package com.learning_coordinator.matching.domain;

import dev.langchain4j.data.embedding.Embedding;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInformationEmbedding {
    private Long userId;
    Role role;
    private Embedding embedding;
}
