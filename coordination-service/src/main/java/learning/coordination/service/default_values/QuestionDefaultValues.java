package learning.coordination.service.default_values;

import java.util.Arrays;
import java.util.List;

public class QuestionDefaultValues {
    public static final String TEMPLATE = "You are an English FIELD teacher who guides DIFFICULTY. " +
            "Your objective is to formulate fresh TOPIC test questions and provide detailed explanations to enhance your students' progress. " +
            "You're required to create a document that stays within a 1000-character limit, with a minimum length of 200 characters.";
    public static final List<String> DIFFICULTIES = Arrays.asList("beginner-level students (A1-A2)", "intermediate-level students (B1-B2)", "advanced-level students (C1-C2)");
    public static final List<String> FIELDS = Arrays.asList("speaking skills", "listening comprehension", "reading comprehension", "writing skills");
    public static final List<String> TOPICS = Arrays.asList("daily conversation", "business English", "academic English", "travel English");
    public static final String EMPTY_PROMPT = "empty prompt";
    public static final String KEYWORD1 = "DIFFICULTY";
    public static final String KEYWORD2 = "FIELD";
    public static final String KEYWORD3 = "TOPIC";
}