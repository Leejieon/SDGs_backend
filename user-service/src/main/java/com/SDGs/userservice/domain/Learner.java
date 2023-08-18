package com.SDGs.userservice.domain;

import javax.persistence.*;

@Entity
@Table(name = "Learner")
public class Learner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long learnerId;

    private Long userId;

    private String strength;

    private String weakness;

    private String wish;

    private String purpose;

    private int attendances = 0;

}
