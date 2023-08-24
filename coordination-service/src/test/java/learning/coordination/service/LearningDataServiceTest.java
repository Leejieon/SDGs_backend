package learning.coordination.service;

import learning.coordination.dto.learning_data.CompletedLearningData;
import learning.coordination.service.default_values.LearningDataDefaultValues;
import learning.coordination.service.test_values.LearningDataTestValues;
import learning.coordination.service.test_values.LearningInformationTestValues;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LearningDataServiceTest {

    @Autowired
    private LearningDataService learningDataService;

    @BeforeEach
    public void initQuestion() {
        // given
        learningDataService.initLearningData(LearningDataTestValues.TEST_ID);
    }

    @Test
    public void shouldFindAnswer() {
        // when
        String expectedAnswer = LearningDataDefaultValues.EMPTY_ANSWER;
        String resultAnswer = learningDataService.findAnswer(LearningDataTestValues.TEST_ID);
        //given
        Assertions.assertThat(resultAnswer).isEqualTo(expectedAnswer);
    }

    @Test
    public void shouldUpdateAnswer() {
        // when
        learningDataService.updateAnswer(LearningDataTestValues.TEST_ID, LearningDataTestValues.TEST_ANSWER);
        String resultUpdatedAnswer = learningDataService.findAnswer(LearningDataTestValues.TEST_ID);
        //then
        Assertions.assertThat(resultUpdatedAnswer).isEqualTo(LearningDataTestValues.TEST_ANSWER);
    }

    @Test
    public void shouldSetLearningTarget() {
        // when
        learningDataService.setLearningTarget(LearningDataTestValues.TEST_ID, LearningDataTestValues.TEST_LEARNING_TARGET);
        String resultLearningTarget = learningDataService.getCompletedLearningData(LearningDataTestValues.TEST_ID).getLearningTarget();
        // then
        Assertions.assertThat(resultLearningTarget).isEqualTo(LearningDataTestValues.TEST_LEARNING_TARGET);
    }

    @Test
    public void shouldSetMaterial() {
        // when
        learningDataService.setMaterial(1L, LearningDataTestValues.TEST_MATERIAL);
        String resultMaterial = learningDataService.getCompletedLearningData(LearningDataTestValues.TEST_ID).getMaterial();
        // then
        Assertions.assertThat(resultMaterial).isEqualTo(LearningDataTestValues.TEST_MATERIAL);
    }

    @Test
    public void shouldGetCompletedLearningData() {
        // when
        learningDataService.updateAnswer(LearningDataTestValues.TEST_ID, LearningDataTestValues.TEST_ANSWER);
        learningDataService.setLearningTarget(LearningDataTestValues.TEST_ID, LearningDataTestValues.TEST_LEARNING_TARGET);
        learningDataService.setMaterial(LearningDataTestValues.TEST_ID, LearningDataTestValues.TEST_MATERIAL);
        CompletedLearningData resultCompletedLearningData = learningDataService.getCompletedLearningData(LearningDataTestValues.TEST_ID);
        // then
        Assertions.assertThat(resultCompletedLearningData.getModifiedAnswer()).isEqualTo(LearningDataTestValues.TEST_ANSWER);
        Assertions.assertThat(resultCompletedLearningData.getLearningTarget()).isEqualTo(LearningDataTestValues.TEST_LEARNING_TARGET);
        Assertions.assertThat(resultCompletedLearningData.getMaterial()).isEqualTo(LearningDataTestValues.TEST_MATERIAL);
    }
}