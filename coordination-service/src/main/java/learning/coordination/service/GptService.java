package learning.coordination.service;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import learning.coordination.service.api_keys.GptToken;
import learning.coordination.service.default_values.GptDefaultValues;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GptService {

    private final QuestionService questionService;
    private final LearningDataService learningDataService;

    private final OpenAiService openAiService = new OpenAiService(GptToken.GPT_API_TOKEN, Duration.ofSeconds(30));

    public void setAnswer(Long id) {
        String prompt = questionService.findPromptById(id);
        String answer = generateAnswer(prompt);

        learningDataService.initLearningData(id);
        learningDataService.updateAnswer(id, answer);
    }

    private String generateAnswer(String prompt) {
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), prompt));

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(GptDefaultValues.MODEL)
                .messages(messages)
                .maxTokens(GptDefaultValues.MAX_TOKENS)
                .n(1)
                .build();

        StringBuilder sentence = new StringBuilder();

        openAiService.streamChatCompletion(chatCompletionRequest)
                .doOnError(Throwable::printStackTrace)
                .blockingForEach(chatCompletionChunk ->
                        chatCompletionChunk.getChoices().stream()
                                .map(ChatCompletionChoice::getMessage)
                                .map(ChatMessage::getContent)
                                .forEach(content -> sentence.append(content).append("")));

        return sentence.toString();
    }
}
