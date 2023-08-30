package com.learning_coordinator.matching.repository;

import java.util.Set;

public interface LearnerMatchingHistoryRepository {
    public void updateMatchingHistory(Long matchingUserId, Long matchingOpponentId, Boolean flag);
    public Set<Long> getMatchingHistory(Long matchingUserId);
}
