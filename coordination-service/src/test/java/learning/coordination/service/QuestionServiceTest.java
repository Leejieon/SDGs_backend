package learning.coordination.service;

import learning.coordination.dto.question.EnglishKeywords;
import learning.coordination.dto.question.SelectedEnglishKeywords;
import learning.coordination.service.default_values.QuestionDefaultValues;
import learning.coordination.service.test_values.QuestionTestValues;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @BeforeEach
    public void initQuestion() {
        // given
        questionService.initQuestion(QuestionTestValues.TEST_ID);
    }

    @Test
    public void shouldFindTemplateById() {
        // when
        String resultTemplate = questionService.findTemplateById(QuestionTestValues.TEST_ID);
        // then
        Assertions.assertThat(resultTemplate).isEqualTo(QuestionDefaultValues.TEMPLATE);
    }

    @Test
    public void shouldFindEnglishKeywordsById() {
        // when
        EnglishKeywords resultEnglishKeywords = questionService.findEnglishKeywordsById(QuestionTestValues.TEST_ID);
        // then
        Assertions.assertThat(resultEnglishKeywords.getDifficulties()).containsExactlyElementsOf(QuestionDefaultValues.DIFFICULTIES);
        Assertions.assertThat(resultEnglishKeywords.getFields()).containsExactlyElementsOf(QuestionDefaultValues.FIELDS);
        Assertions.assertThat(resultEnglishKeywords.getTopics()).containsExactlyElementsOf(QuestionDefaultValues.TOPICS);
    }

    @Test
    public void shouldFindPromptById() {
        // when
        String resultPrompt = questionService.findPromptById(QuestionTestValues.TEST_ID);
        // then
        Assertions.assertThat(resultPrompt).isEqualTo(QuestionDefaultValues.EMPTY_PROMPT);
    }
    @Test
    public void shouldUpdatePromptById() {
        // when
        questionService.updatePromptById(QuestionTestValues.TEST_ID, QuestionTestValues.TEST_PROMPT);
        String resultPrompt = questionService.findPromptById(QuestionTestValues.TEST_ID);
        // then
        Assertions.assertThat(resultPrompt).isEqualTo(QuestionTestValues.TEST_PROMPT);
    }

    @Test
    public void shouldSelectPromptById() {
        // given
        SelectedEnglishKeywords selectedEnglishKeywords =
                SelectedEnglishKeywords.builder()
                        .difficulty(QuestionDefaultValues.DIFFICULTIES.get(0))
                        .field(QuestionDefaultValues.FIELDS.get(0))
                        .topic(QuestionDefaultValues.TOPICS.get(0))
                        .build();

        // when
        questionService.selectPromptById(QuestionTestValues.TEST_ID, selectedEnglishKeywords);
        String defaultPrompt = QuestionDefaultValues.TEMPLATE;
        String expectedPrompt = generateExpectedPrompt(defaultPrompt);
        // then
        Assertions.assertThat(questionService.findPromptById(QuestionTestValues.TEST_ID)).isEqualTo(expectedPrompt);
    }

    private String generateExpectedPrompt(String defaultPrompt) {
        return defaultPrompt.replace(QuestionDefaultValues.KEYWORD1, QuestionDefaultValues.DIFFICULTIES.get(0))
                .replace(QuestionDefaultValues.KEYWORD2, QuestionDefaultValues.FIELDS.get(0))
                .replace(QuestionDefaultValues.KEYWORD3, QuestionDefaultValues.TOPICS.get(0));
    }
}