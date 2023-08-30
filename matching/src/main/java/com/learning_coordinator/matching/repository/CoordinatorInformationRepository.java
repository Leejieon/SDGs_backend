package com.learning_coordinator.matching.repository;

import com.learning_coordinator.matching.domain.CoordinatorInformation;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CoordinatorInformationRepository extends JpaRepository<CoordinatorInformation, Long>{
    Optional<CoordinatorInformation> findByUserId(Long userid); 
}
