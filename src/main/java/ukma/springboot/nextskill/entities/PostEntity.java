package ukma.springboot.nextskill.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "posts")
public class PostEntity extends CourseObjectEntity{

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ManyToMany
    @JoinTable(name = "post_attached_files",
            joinColumns = @JoinColumn(name = "post_fk"),
            inverseJoinColumns = @JoinColumn(name = "file_upload_fk")
    )
    private List<FileUploadEntity> attachedFiles;

    protected PostEntity() {
        super();
    }

    public PostEntity(String title, String content, List<FileUploadEntity> attachedFiles) {
        super(title);
        this.content = content;
        this.attachedFiles = attachedFiles;
    }

    public PostEntity(UUID uuid, String title, int order, boolean isHidden, LocalDateTime createdAt) {
        super(uuid, title, order, isHidden, createdAt);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<FileUploadEntity> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<FileUploadEntity> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }
}
