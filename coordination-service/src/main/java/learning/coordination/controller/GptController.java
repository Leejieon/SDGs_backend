package learning.coordination.controller;

import learning.coordination.controller.default_values.ControllerDefaults;
import learning.coordination.dto.response.MessageResponse;
import learning.coordination.dto.request.CallGptRequest;
import learning.coordination.service.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class GptController {

    private final GptService gptService;

    @PostMapping("answer")
    public ResponseEntity<MessageResponse> callGpt(@RequestBody CallGptRequest callGptRequest) {
        gptService.setAnswer(callGptRequest.getId());
        return ResponseEntity.ok(new MessageResponse(ControllerDefaults.CALLED_GPT_SUCCESS));
    }
}
