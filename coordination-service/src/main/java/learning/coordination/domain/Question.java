package learning.coordination.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "template", nullable = false, length = 1024)
    private String template;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "difficulties", joinColumns = @JoinColumn(name = "difficulty_id"))
    private List<String> difficulties;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "fields", joinColumns = @JoinColumn(name = "field_id"))
    private List<String> fields;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "topics", joinColumns = @JoinColumn(name = "topic_id"))
    private List<String> topics;

    @Column(name = "prompt", nullable = false, length = 1024)
    private String prompt;

    @Builder
    public Question(String template, List<String> difficulties, List<String> fields, List<String> topics, String prompt, Long id) {
        this.template = template;
        this.difficulties = difficulties;
        this.fields = fields;
        this.topics = topics;
        this.prompt = prompt;
        this.id = id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}