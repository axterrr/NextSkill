package ukma.springboot.nextskill.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "post")
public class PostEntity extends CourseObjectEntity{

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ElementCollection
    @CollectionTable(name = "post_attached_files", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "attached_file_url")
    private List<String> attachedFiles;

    protected PostEntity() {
        super();
    }

    public PostEntity(String title, String content, List<String> attachedFiles) {
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

    public List<String> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<String> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }
}
