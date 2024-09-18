package ukma.springboot.nextskill.model;

import java.util.List;

public class Post extends CourseObject{
    private String content;                 //Should we add markdown support?
    private List<String> attachedFiles;     //Separate class?

    public Post(String title, String content, List<String> attachedFiles) {
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
