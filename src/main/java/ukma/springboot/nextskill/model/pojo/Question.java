package ukma.springboot.nextskill.model.pojo;

import java.util.List;
import java.util.UUID;

public class Question {
    private final UUID id;
    private String text;
    private List<String> attachedImages;
    private List<AnswerOption> answerOptions;
    private boolean isMultipleChoice;

    public Question(String text, List<String> attachedImages, List<AnswerOption> answerOptions, boolean isMultipleChoice) {
        this.id = UUID.randomUUID();
        this.text = text;
        this.attachedImages = attachedImages;
        this.answerOptions = answerOptions;
        this.isMultipleChoice = isMultipleChoice;
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

    public List<String> getAttachedImages() {
        return attachedImages;
    }

    public void setAttachedImages(List<String> attachedImages) {
        this.attachedImages = attachedImages;
    }

    public List<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<AnswerOption> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public boolean isMultipleChoice() {
        return isMultipleChoice;
    }

    public void setMultipleChoice(boolean multipleChoice) {
        isMultipleChoice = multipleChoice;
    }
}
