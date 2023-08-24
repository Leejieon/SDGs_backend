package learning.coordination.controller;

import learning.coordination.controller.default_values.ControllerDefaults;
import learning.coordination.dto.request.ModifyPromptRequest;
import learning.coordination.dto.request.SelectEnglishKeywordsRequest;
import learning.coordination.dto.response.MessageResponse;
import learning.coordination.dto.question.*;
import learning.coordination.dto.response.PromptResponse;
import learning.coordination.dto.response.TemplateResponse;
import learning.coordination.exception.InputValidator;
import learning.coordination.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final QuestionService questionService;
    private final InputValidator inputValidator;


    @GetMapping("template/{id}")
    public ResponseEntity<TemplateResponse> findTemplateById(@PathVariable Long id) {
        TemplateResponse template = TemplateResponse.builder()
                .template(questionService.findTemplateById(id)).build();
        return ResponseEntity.ok(template);
    }

    @GetMapping("english-keywords/{id}")
    public ResponseEntity<EnglishKeywords> findEnglishKeywords(@PathVariable Long id) {
        EnglishKeywords englishKeywords = questionService.findEnglishKeywordsById(id);
        return ResponseEntity.ok(englishKeywords);
    }

    @PostMapping("english-keywords")
    public ResponseEntity<MessageResponse> selectEnglishKeywords(@RequestBody SelectEnglishKeywordsRequest selectEnglishKeywordRequest) {
        inputValidator.validateSelectedEnglishKeywords(selectEnglishKeywordRequest.getSelectedEnglishKeywords());
        if (!inputValidator.isValid()) {
            return ResponseEntity.badRequest().body(new MessageResponse(inputValidator.getMessage()));
        }

        questionService.selectPromptById(
                selectEnglishKeywordRequest.getId(),
                selectEnglishKeywordRequest.getSelectedEnglishKeywords());
        return ResponseEntity.ok(new MessageResponse(ControllerDefaults.SELECTED_ENGLISH_KEYWORDS_SUCCESS));
    }

    @GetMapping("prompt/{id}")
    public ResponseEntity<PromptResponse> findPrompt(@PathVariable Long id) {
        PromptResponse prompt = PromptResponse.builder()
                .prompt(questionService.findPromptById(id)).build();
        return ResponseEntity.ok(prompt);
    }

    @PutMapping("prompt")
    public ResponseEntity<MessageResponse> modifyPrompt(@RequestBody ModifyPromptRequest modifyPromptRequest) {
        inputValidator.validateString(modifyPromptRequest.getModifiedPrompt());
        if (!inputValidator.isValid()) {
            return ResponseEntity.badRequest().body(new MessageResponse(inputValidator.getMessage()));
        }

        questionService.updatePromptById(
                modifyPromptRequest.getId(),
                modifyPromptRequest.getModifiedPrompt());
        return ResponseEntity.ok(new MessageResponse(ControllerDefaults.MODIFIED_PROMPT_SUCCESS));
    }
}