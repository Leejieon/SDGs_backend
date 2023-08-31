package learning.coordination.controller;

import learning.coordination.controller.default_values.ControllerDefaults;
import learning.coordination.dto.response.MessageResponse;
import learning.coordination.dto.learning_information.CompletedLearningInformation;
import learning.coordination.dto.request.SetMisUnderstandingThingsRequest;
import learning.coordination.dto.request.SetUnderstandingThingsRequest;
import learning.coordination.exception.InputValidator;
import learning.coordination.service.LearningInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LearningInformationController {

    private final LearningInformationService learningInformationService;
    private final InputValidator inputValidator;

    @PostMapping("understanding-things")
    public ResponseEntity<MessageResponse> setUnderstandingThings(@RequestBody SetUnderstandingThingsRequest setUnderstandingThingsRequest) {
        inputValidator.validateString(setUnderstandingThingsRequest.getUnderstandingThings());
        if (!inputValidator.isValid()) {
            return ResponseEntity.badRequest().body(new MessageResponse(inputValidator.getMessage()));
        }

        learningInformationService.setUnderstandingThings(
                setUnderstandingThingsRequest.getId(),
                setUnderstandingThingsRequest.getUnderstandingThings());
        return ResponseEntity.ok(new MessageResponse(ControllerDefaults.SET_UNDERSTANDING_THINGS_SUCCESS));
    }

    @PostMapping("misunderstanding-things")
    public ResponseEntity<MessageResponse> setMisUnderstandingThings(@RequestBody SetMisUnderstandingThingsRequest setMisUnderstandingThingsRequest) {
        inputValidator.validateString(setMisUnderstandingThingsRequest.getMisUnderstandingThings());
        if (!inputValidator.isValid()) {
            return ResponseEntity.badRequest().body(new MessageResponse(inputValidator.getMessage()));
        }

        learningInformationService.setMisUnderstandingThings(
                setMisUnderstandingThingsRequest.getId(),
                setMisUnderstandingThingsRequest.getMisUnderstandingThings());
        return ResponseEntity.ok(new MessageResponse(ControllerDefaults.SET_MISUNDERSTANDING_THINGS_SUCCESS));
    }

    @GetMapping("completed-learning-information/{id}")
    public ResponseEntity<CompletedLearningInformation> getCompletedLearningInformation(@PathVariable Long id) {
        CompletedLearningInformation completedLearningInformation = learningInformationService.getCompletedLearningInformation(id);
        return ResponseEntity.ok(completedLearningInformation);
    }
}