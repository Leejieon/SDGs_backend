package com.learning_coordinator.matching.domain;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.objectdb.o.Enhancement;

import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@PersistenceCapable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Enhancement
public class UserInformationEmbeddingStore {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    private int id;

    @Persistent
    private Date lastbatchdate;
    
    @Persistent
    private InMemoryEmbeddingStore<UserInformationEmbedding> embeddingStore;
}