package com.SDGs.userservice.repository;

import com.SDGs.userservice.domain.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoordinatorRepository extends JpaRepository<Coordinator, Long> {
    Optional<Coordinator> findByUserId(Long userId);
}
