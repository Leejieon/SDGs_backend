package learning.coordination.exception;

import learning.coordination.dto.question.SelectedEnglishKeywords;
import learning.coordination.service.default_values.QuestionDefaultValues;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class InputValidator {
    private boolean valid;
    private String message;

    private static final String STRING_ERROR_NULL = "Invalid String: Input String cannot be null";
    private static final String STRING_ERROR_EMPTY = "Invalid String: Input String cannot be empty";
    private static final String STRING_ERROR_LENGTH = "Invalid String: Input String length cannot exceed 1024 characters";
    private static final String STRING_SUCCESS = "Valid String: Validation successful";
    private static final String ENGLISH_KEYWORDS_ERROR_DIFFICULTY = "Invalid English Keywords: No default value for difficulty.";
    private static final String ENGLISH_KEYWORDS_ERROR_FIELD = "Invalid English Keywords: No default value for field.";
    private static final String ENGLISH_KEYWORDS_ERROR_TOPIC = "Invalid English Keywords: No default value for topic.";
    private static final String ENGLISH_KEYWORDS_SUCCESS = "Valid English Keywords: Validation successful.";
    public void validateString(String inputString) {
        if (inputString == null) {
            setValidationAndMessage(false, STRING_ERROR_NULL);
        } else if (inputString.isEmpty()) {
            setValidationAndMessage(false, STRING_ERROR_EMPTY);
        } else if (inputString.length() > 1024) {
            setValidationAndMessage(false, STRING_ERROR_LENGTH);
        } else {
            setValidationAndMessage(true, STRING_SUCCESS);
        }
    }
    public void validateSelectedEnglishKeywords(SelectedEnglishKeywords selectedEnglishKeywords) {
        if (!QuestionDefaultValues.DIFFICULTIES.contains(selectedEnglishKeywords.getDifficulty())) {
            setValidationAndMessage(false, ENGLISH_KEYWORDS_ERROR_DIFFICULTY);
        } else if (!QuestionDefaultValues.FIELDS.contains(selectedEnglishKeywords.getField())) {
            setValidationAndMessage(false, ENGLISH_KEYWORDS_ERROR_FIELD);
        } else if (!QuestionDefaultValues.TOPICS.contains(selectedEnglishKeywords.getTopic())) {
            setValidationAndMessage(false, ENGLISH_KEYWORDS_ERROR_TOPIC);
        } else {
            setValidationAndMessage(true, ENGLISH_KEYWORDS_SUCCESS);
        }
    }

    private void setValidationAndMessage(boolean validation, String message) {
        this.valid = validation;
        this.message = message;
    }
}
