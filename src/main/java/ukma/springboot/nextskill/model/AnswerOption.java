package ukma.springboot.nextskill.model;

import java.util.Random;

public class AnswerOption {
    private final int id;           //Random number from 100000 to 999998
    private String text;
    private boolean isCorrect;

    public AnswerOption() {
        this.id = generateRandomId();
    }

    private int generateRandomId() {
        Random random = new Random();
        return random.nextInt(100000, 999999);
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
