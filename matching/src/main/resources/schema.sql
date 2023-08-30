DROP TABLE IF EXISTS Matching;
CREATE TABLE Matching (
    MatchingId BIGINT AUTO_INCREMENT PRIMARY KEY,
    CoordinatorId BIGINT,
    LearnerId BIGINT
);