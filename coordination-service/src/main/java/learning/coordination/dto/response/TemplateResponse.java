package learning.coordination.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TemplateResponse {
    private String template;
    @Builder
    public TemplateResponse(String template) {
        this.template = template;
    }
}
