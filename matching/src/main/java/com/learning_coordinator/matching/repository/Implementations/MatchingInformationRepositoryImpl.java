package com.learning_coordinator.matching.repository.Implementations;

import java.util.Collections;
import java.util.List;

import com.learning_coordinator.matching.domain.MatchingInformation;
import com.learning_coordinator.matching.dto.MatchingInformationDTO;
import com.learning_coordinator.matching.repository.MatchingInformationRepository;

import jakarta.transaction.Transactional;
public class MatchingInformationRepositoryImpl implements MatchingInformationRepository {

    @Override
    @Transactional
    public void insertData(MatchingInformation domain) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertData'");
    }

    @Override
    public List<MatchingInformationDTO> getMatchingOppenentsList() {
        return Collections.emptyList();
    }

    public List<Long> getMatchingOppenentIdsList() {
        return Collections.emptyList();
    }
    
}
