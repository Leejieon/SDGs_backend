package learning.coordination.dto.question;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
public class EnglishKeywords {
    private List<String> difficulties;
    private List<String> fields;
    private List<String> topics;
    @Builder
    public EnglishKeywords(List<String> difficulties, List<String> fields, List<String> topics) {
        this.difficulties = difficulties;
        this.fields = fields;
        this.topics = topics;
    }
}