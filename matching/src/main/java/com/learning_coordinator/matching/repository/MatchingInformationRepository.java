package com.learning_coordinator.matching.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.learning_coordinator.matching.domain.MatchingInformation;
import com.learning_coordinator.matching.dto.MatchingInformationDTO;

@Repository
public interface MatchingInformationRepository{
    public void insertData(MatchingInformation domain);
    public List<MatchingInformationDTO> getMatchingOppenentsList(); 
}

