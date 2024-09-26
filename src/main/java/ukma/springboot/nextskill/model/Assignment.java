package ukma.springboot.nextskill.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Assignment extends Post{

    private LocalDateTime dueTo;
    private int grade;

    public Assignment(String title, String content, List<FileUpload> attachedFiles) {
        super(title, content, attachedFiles);
        this.dueTo = LocalDateTime.now().plusDays(1);
    }

    public Assignment(String title, UUID id, LocalDateTime createdAt, boolean isHidden, int order) {
        super(title, id, createdAt, isHidden, order);
    }

    public LocalDateTime getDueTo() {
        return dueTo;
    }

    public void setDueTo(LocalDateTime dueTo) {
        this.dueTo = dueTo;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
