package learning.coordination.repository;

import learning.coordination.domain.LearningInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningInformationRepository extends JpaRepository<LearningInformation, Long> {
}
