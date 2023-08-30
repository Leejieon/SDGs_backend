package com.learning_coordinator.matching.domain;

import jakarta.persistence.*;

@Entity
public class CoordinatorInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coordinatorid", updatable = false, unique = true)
    private Long coordinatorId;

    private Long userId;
    private String field;
    private String strength;
}
