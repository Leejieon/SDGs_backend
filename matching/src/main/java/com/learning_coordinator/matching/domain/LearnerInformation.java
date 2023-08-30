package com.learning_coordinator.matching.domain;

import jakarta.persistence.*;

@Entity
public class LearnerInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "learnerId", updatable = false, unique = true)
    private Long learnerId;

    private Long userId;
    private String strength;
    private String weakness;
    private String wish;
    private String pupose;
    private int attendances = 0;
}
