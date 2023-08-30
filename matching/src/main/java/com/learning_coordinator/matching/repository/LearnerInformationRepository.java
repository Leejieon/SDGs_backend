package com.learning_coordinator.matching.repository;

import com.learning_coordinator.matching.domain.LearnerInformation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LearnerInformationRepository extends JpaRepository<LearnerInformation, Long>{
    Optional<LearnerInformation> findByUserId(Long userid); 
}
