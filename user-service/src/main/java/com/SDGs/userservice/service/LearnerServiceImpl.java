package com.SDGs.userservice.service;

import com.SDGs.userservice.dto.learner.LearnerDto;
import com.SDGs.userservice.domain.Learner;
import com.SDGs.userservice.repository.LearnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LearnerServiceImpl implements LearnerService{

    private final LearnerRepository learnerRepository;

    @Override
    @Transactional
    public LearnerDto register(LearnerDto learnerDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Learner learner = mapper.map(learnerDto, Learner.class);
        learnerRepository.save(learner);

        return mapper.map(learner, LearnerDto.class);
    }
}
