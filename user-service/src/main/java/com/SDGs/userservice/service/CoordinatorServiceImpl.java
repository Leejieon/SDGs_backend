package com.SDGs.userservice.service;

import com.SDGs.userservice.domain.Coordinator;
import com.SDGs.userservice.dto.coordinator.CoordinatorDto;
import com.SDGs.userservice.repository.CoordinatorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoordinatorServiceImpl implements CoordinatorService{

    private final CoordinatorRepository coordinatorRepository;

    @Override
    @Transactional
    public CoordinatorDto register(CoordinatorDto coordinatorDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Coordinator coordinator = mapper.map(coordinatorDto, Coordinator.class);
        coordinatorRepository.save(coordinator);

        return mapper.map(coordinator, CoordinatorDto.class);
    }
}
