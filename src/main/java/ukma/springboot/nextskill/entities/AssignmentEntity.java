package ukma.springboot.nextskill.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "assignments")
public class AssignmentEntity extends PostEntity{

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "due_to", nullable = false)
    private LocalDateTime dueTo;
    private Integer grade;
    public AssignmentEntity() {
        super();
        this.dueTo = LocalDateTime.now().plusDays(1);
    }

    public AssignmentEntity(String title, String content, List<FileUploadEntity> attachedFiles) {
        super(title, content, attachedFiles);
        this.dueTo = LocalDateTime.now().plusDays(1);
    }

    public AssignmentEntity(UUID uuid, String title, int order, boolean isHidden, LocalDateTime createdAt) {
        super(uuid, title, order, isHidden, createdAt);
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public LocalDateTime getDueTo() {
        return dueTo;
    }

    public void setDueTo(LocalDateTime dueTo) {
        this.dueTo = dueTo;
    }
}
