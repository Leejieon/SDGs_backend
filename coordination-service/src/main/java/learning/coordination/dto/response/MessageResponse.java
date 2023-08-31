package learning.coordination.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageResponse {
    private String message;
    public MessageResponse(String message) {
        this.message = message;
    }
}
