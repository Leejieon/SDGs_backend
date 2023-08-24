package learning.coordination.repository;

import learning.coordination.domain.LearningData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningDataRepository extends JpaRepository<LearningData, Long> {
}
