package learning.coordination.dto.request;

import lombok.Getter;

@Getter
public class UpdateAnswerRequest {
    private Long id;
    private String modifiedAnswer;
}
