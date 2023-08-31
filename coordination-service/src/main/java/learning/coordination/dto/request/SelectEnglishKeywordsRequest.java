package learning.coordination.dto.request;

import learning.coordination.dto.question.SelectedEnglishKeywords;
import lombok.Getter;

@Getter
public class SelectEnglishKeywordsRequest {
    private Long id;
    private SelectedEnglishKeywords selectedEnglishKeywords;
}
