package learning.coordination.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LearningInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "understandingThings", nullable = false, length = 1024)
    private String understandingThings;

    @Column(name = "misUnderstandingThings", nullable = false, length = 1024)
    private String misUnderstandingThings;

    @Builder
    public LearningInformation(String understandingThings, String misUnderstandingThings, Long id) {
        this.understandingThings = understandingThings;
        this.misUnderstandingThings = misUnderstandingThings;
        this.id = id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setUnderstandingThings(String understandingThings) {
        this.understandingThings = understandingThings;
    }
    public void setMisUnderstandingThings(String misUnderstandingThings) {
        this.misUnderstandingThings = misUnderstandingThings;
    }
}
