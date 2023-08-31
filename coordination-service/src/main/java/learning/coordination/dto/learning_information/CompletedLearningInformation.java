package learning.coordination.dto.learning_information;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CompletedLearningInformation {

    private String understandingThings;
    private String misUnderstandingThings;

    @Builder
    public CompletedLearningInformation(String understandingThings, String misUnderstandingThings) {
        this.understandingThings = understandingThings;
        this.misUnderstandingThings = misUnderstandingThings;
    }
}
