package learning.coordination.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AnswerResponse {
    private String answer;
    @Builder
    public AnswerResponse(String answer) {
        this.answer = answer;
    }
}
