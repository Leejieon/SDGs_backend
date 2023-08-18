package com.SDGs.userservice.repository;

import com.SDGs.userservice.domain.Learner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LearnerRepository extends JpaRepository<Learner, Long> {
    Optional<Learner> findByUserId(Long userId);
}
