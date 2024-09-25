package ukma.springboot.nextskill.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "post")
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
