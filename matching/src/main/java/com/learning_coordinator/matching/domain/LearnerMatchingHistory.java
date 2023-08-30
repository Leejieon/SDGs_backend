package com.learning_coordinator.matching.domain;

import java.util.Set;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@PersistenceCapable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LearnerMatchingHistory {
    @PrimaryKey
    @Persistent
    private Long id;
    @Persistent
    private Set<Long> matchingHistory;
}
