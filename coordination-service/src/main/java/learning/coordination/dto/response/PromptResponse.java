package learning.coordination.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PromptResponse {
    private String prompt;
    @Builder
    public PromptResponse(String prompt) {
        this.prompt = prompt;
    }
}
