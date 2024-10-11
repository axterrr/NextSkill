package ukma.springboot.nextskill.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "answers")
public class AnswerOptionEntity {
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false, unique = true)
    private final UUID uuid;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private boolean isCorrect;

    public AnswerOptionEntity() {
        this.uuid = UUID.randomUUID();
    }

    public UUID getId() {
        return uuid;
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
