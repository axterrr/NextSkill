package ukma.springboot.nextskill.model.pojo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Post extends CourseObject{
    private String content;                 //Should we add markdown support?
    private List<FileUpload> attachedFiles;

    public Post(String title, String content, List<FileUpload> attachedFiles) {
        super(title);
        this.content = content;
        this.attachedFiles = attachedFiles;
    }

    public Post(String title, UUID id, LocalDateTime createdAt, boolean isHidden, int order) {
        super(title, id, createdAt, isHidden, order);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<FileUpload> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<FileUpload> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

}
