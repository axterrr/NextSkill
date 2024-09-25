package ukma.springboot.nextskill.entities;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "questions")
public class QuestionEntity {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false, unique = true)
    private final UUID id;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private boolean isMultipleChoice;
    @ManyToMany
    @JoinTable(name = "question_attached_files",
            joinColumns = @JoinColumn(name = "question_fk"),
            inverseJoinColumns = @JoinColumn(name = "file_upload_fk")
    )

    private List<FileUploadEntity> attachedFiles;
    @Column
    @OneToMany
    @JoinTable(name = "question_answer_options",
            joinColumns = @JoinColumn(name = "question_fk"),
            inverseJoinColumns = @JoinColumn(name = "answer_option_fk"))
    private List<AnswerOptionEntity> answerOptions;

    public QuestionEntity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isMultipleChoice() {
        return isMultipleChoice;
    }

    public void setMultipleChoice(boolean multipleChoice) {
        isMultipleChoice = multipleChoice;
    }

    public List<AnswerOptionEntity> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<AnswerOptionEntity> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public List<FileUploadEntity> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<FileUploadEntity> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }
}
