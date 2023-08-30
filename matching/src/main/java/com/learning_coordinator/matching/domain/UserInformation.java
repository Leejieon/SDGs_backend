package com.learning_coordinator.matching.domain;

import java.util.Date;

import jakarta.persistence.*;

@Entity
public class UserInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid", updatable = false, unique = true)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    private String name;
    private String email;
    private boolean matching;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
}
