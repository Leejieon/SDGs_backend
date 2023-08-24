package learning.coordination.dto.learning_data;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CompletedLearningData {

    private String modifiedAnswer;
    private String learningTarget;
    private String material;

    @Builder
    public CompletedLearningData(String modifiedAnswer, String learningTarget, String material) {
        this.modifiedAnswer = modifiedAnswer;
        this.learningTarget = learningTarget;
        this.material = material;
    }
}
