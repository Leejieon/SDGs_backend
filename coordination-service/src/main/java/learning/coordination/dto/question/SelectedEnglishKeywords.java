package learning.coordination.dto.question;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SelectedEnglishKeywords {
    private String difficulty;
    private String field;
    private String topic;

    @Builder
    public SelectedEnglishKeywords(String difficulty, String field, String topic) {
        this.difficulty = difficulty;
        this.field = field;
        this.topic = topic;
    }
}