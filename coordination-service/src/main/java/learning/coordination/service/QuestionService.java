package learning.coordination.service;

import learning.coordination.domain.Question;
import learning.coordination.dto.question.EnglishKeywords;
import learning.coordination.dto.question.SelectedEnglishKeywords;
import learning.coordination.repository.QuestionRepository;
import learning.coordination.service.default_values.QuestionDefaultValues;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public void initQuestion(Long id) {
        Question question = createDefaultQuestion(id);
        saveQuestion(question);
    }

    public void selectPromptById(Long id, SelectedEnglishKeywords selectedEnglishKeywords) {
        String template = findTemplateById(id);
        String modifiedTemplate = replaceKeywordsInTemplate(selectedEnglishKeywords, template);
        updatePromptById(id, modifiedTemplate);
    }

    public void updatePromptById(Long id, String modifiedPrompt) {
        Question question = findQuestion(id);
        question.setPrompt(modifiedPrompt);
        saveQuestion(question);
    }

    public String findTemplateById(Long id) {
        Question question = findQuestion(id);
        return question.getTemplate();
    }

    public EnglishKeywords findEnglishKeywordsById(Long id) {
        Question question = findQuestion(id);
        return EnglishKeywords.builder()
                .difficulties(question.getDifficulties())
                .fields(question.getFields())
                .topics(question.getTopics())
                .build();
    }

    public String findPromptById(Long id) {
        Question question = findQuestion(id);
        return question.getPrompt();
    }


    private Question createDefaultQuestion(Long id) {
        return Question.builder()
                .template(QuestionDefaultValues.TEMPLATE)
                .difficulties(QuestionDefaultValues.DIFFICULTIES)
                .fields(QuestionDefaultValues.FIELDS)
                .topics(QuestionDefaultValues.TOPICS)
                .prompt(QuestionDefaultValues.EMPTY_PROMPT)
                .id(id)
                .build();
    }

    private String replaceKeywordsInTemplate(SelectedEnglishKeywords selectedEnglishKeywords, String template) {
        String replacedTemplate = template.replace(QuestionDefaultValues.KEYWORD1, selectedEnglishKeywords.getDifficulty())
                .replace(QuestionDefaultValues.KEYWORD3, selectedEnglishKeywords.getTopic())
                .replace(QuestionDefaultValues.KEYWORD2, selectedEnglishKeywords.getField());
        return replacedTemplate;
    }

    private Question findQuestion(Long id) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        Question question;
        if (!questionOptional.isPresent()) {
            initQuestion(id);
            questionOptional = questionRepository.findById(id);
        }
        question = questionOptional.get();
        return question;
    }

    private void saveQuestion(Question question) {
        questionRepository.save(question);
    }
}