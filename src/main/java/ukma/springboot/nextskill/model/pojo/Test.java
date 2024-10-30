//TODO: UserTestAttempt class

package ukma.springboot.nextskill.model.pojo;

import java.time.LocalDateTime;
import java.util.List;

public class Test extends CourseObject{
    private List<Question> questions;
    private LocalDateTime opensAt;
    private LocalDateTime closesAt;
    private int timeLimit;
    private int maxAttempts;

    public Test(String testName, List<Question> questions, int timeLimit, int maxAttempts) {
        super(testName);
        this.opensAt = LocalDateTime.now().plusDays(1);
        this.closesAt = opensAt.plusDays(7);
        this.questions = questions;
        this.timeLimit = timeLimit;
        this.maxAttempts = maxAttempts;
    }

    public LocalDateTime getOpensAt() {
        return opensAt;
    }

    public void setOpensAt(LocalDateTime opensAt) {
        this.opensAt = opensAt;
    }

    public LocalDateTime getClosesAt() {
        return closesAt;
    }

    public void setClosesAt(LocalDateTime closesAt) {
        this.closesAt = closesAt;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
}
