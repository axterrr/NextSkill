package ukma.springboot.nextskill.model.pojo;

public class AnswerOption {
    private final int id;
    private String text;
    private boolean isCorrect;

    public AnswerOption(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
