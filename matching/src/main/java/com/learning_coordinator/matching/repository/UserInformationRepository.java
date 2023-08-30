package com.learning_coordinator.matching.repository;

import com.learning_coordinator.matching.domain.UserInformation;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInformationRepository extends JpaRepository<UserInformation, Long> {    
    List<UserInformation> findByModifiedDateBeforeOrderByCreatedDateDesc(Date lastbatchdate);
}
