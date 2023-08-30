package com.learning_coordinator.matching.batch;

import com.learning_coordinator.matching.domain.MatchingInformation;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;


public class MatchingInformationRowMapper implements RowMapper<MatchingInformation>{

    @Override
    @Nullable
    public MatchingInformation mapRow(ResultSet rs, int rowNum) throws SQLException {
        return MatchingInformation.builder()
                                .MatchingId(rs.getLong("matching_id"))
                                .CoordinatorId(rs.getLong("coordinator_id"))
                                .LearnerId(rs.getLong("learner_id"))
                                .build();
    }
}
