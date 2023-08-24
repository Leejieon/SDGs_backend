package learning.coordination.service;

import learning.coordination.service.test_values.GptTestValues;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GptServiceTest {

    @Autowired
    private GptService gptService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private LearningDataService learningDataService;

    @Test
    public void testCallGpt() {
/*
        // when
        questionService.updatePromptById(GptTestValues.ID, GptTestValues.TEST_PROMPT);
        gptService.setAnswer(GptTestValues.ID);
        String resultAnswer = learningDataService.findAnswer(GptTestValues.ID);
        // then
        assertThat(resultAnswer).isEqualTo(GptTestValues.TEST_ANSWER);
*/
    }
}