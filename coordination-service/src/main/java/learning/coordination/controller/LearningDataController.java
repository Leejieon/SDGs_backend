package learning.coordination.controller;

import learning.coordination.controller.default_values.ControllerDefaults;
import learning.coordination.dto.request.SetLearningTargetRequest;
import learning.coordination.dto.request.SetMaterialRequest;
import learning.coordination.dto.request.UpdateAnswerRequest;
import learning.coordination.dto.response.AnswerResponse;
import learning.coordination.dto.response.MessageResponse;
import learning.coordination.dto.learning_data.*;
import learning.coordination.exception.InputValidator;
import learning.coordination.service.LearningDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LearningDataController {

    private final LearningDataService learningDataService;
    private final InputValidator inputValidator;

    @GetMapping("answer/{id}")
    public ResponseEntity<AnswerResponse> findAnswer(@PathVariable Long id) {
        return ResponseEntity.ok(AnswerResponse.builder()
                .answer(learningDataService.findAnswer(id))
                .build());
    }

    @PutMapping("answer")
    public ResponseEntity<MessageResponse> updateAnswer(@RequestBody UpdateAnswerRequest updateAnswerRequest) {
        inputValidator.validateString(updateAnswerRequest.getModifiedAnswer());
        if (!inputValidator.isValid()) {
            return ResponseEntity.badRequest().body(new MessageResponse(inputValidator.getMessage()));
        }

        learningDataService.updateAnswer(
                updateAnswerRequest.getId(),
                updateAnswerRequest.getModifiedAnswer());
        return ResponseEntity.ok(new MessageResponse(ControllerDefaults.UPDATED_ANSWER_SUCCESS));
    }

    @PostMapping("learning-target")
    public ResponseEntity<MessageResponse> setLearningTarget(@RequestBody SetLearningTargetRequest setLearningTargetRequest) {
        inputValidator.validateString(setLearningTargetRequest.getLearningTarget());
        if (!inputValidator.isValid()) {
            return ResponseEntity.badRequest().body(new MessageResponse(inputValidator.getMessage()));
        }

        learningDataService.setLearningTarget(
                setLearningTargetRequest.getId(),
                setLearningTargetRequest.getLearningTarget());
        return ResponseEntity.ok(new MessageResponse(ControllerDefaults.SET_LEARNING_TARGET_SUCCESS));
    }

    @PostMapping("material")
    public ResponseEntity<MessageResponse> setMaterial(@RequestBody SetMaterialRequest setMaterialRequest) {
        inputValidator.validateString(setMaterialRequest.getMaterial());
        if (!inputValidator.isValid()) {
            return ResponseEntity.badRequest().body(new MessageResponse(inputValidator.getMessage()));
        }

        learningDataService.setMaterial(
                setMaterialRequest.getId(),
                setMaterialRequest.getMaterial());
        return ResponseEntity.ok(new MessageResponse(ControllerDefaults.SET_MATERIAL_SUCCESS));
    }

    @GetMapping("completed-learning-data/{id}")
    public ResponseEntity<CompletedLearningData> getCompletedLearningData(@PathVariable Long id) {
        return ResponseEntity.ok(learningDataService.getCompletedLearningData(id));
    }
}