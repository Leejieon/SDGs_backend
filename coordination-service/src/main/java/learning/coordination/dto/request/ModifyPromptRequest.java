package learning.coordination.dto.request;

import lombok.Getter;

@Getter
public class ModifyPromptRequest {
    private Long id;
    private String modifiedPrompt;
}
