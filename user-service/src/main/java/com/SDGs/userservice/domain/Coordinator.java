package com.SDGs.userservice.domain;

import javax.persistence.*;

@Entity
@Table(name = "Coordinator")
public class Coordinator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coordinatorId;

    private Long userId;

    private String field;

    private String strength;
}
