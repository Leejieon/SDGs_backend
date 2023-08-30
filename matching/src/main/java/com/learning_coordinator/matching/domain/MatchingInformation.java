package com.learning_coordinator.matching.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Matching")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchingInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MatchingId", updatable = false, unique = true)
    private Long MatchingId;

    private Long CoordinatorId;
    private Long LearnerId;

    public void setCoordinatorId(Long CoordinatorId){
        this.CoordinatorId = CoordinatorId;
    }
    public void setLearnerId(Long LearnerId){
        this.LearnerId = LearnerId;
    }

    public Long getCoordinatorId(){
        return this.CoordinatorId;
    }
    public Long getLearnerId(){
        return LearnerId;
    }
}
