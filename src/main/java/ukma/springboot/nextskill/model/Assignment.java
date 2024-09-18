package ukma.springboot.nextskill.model;

import java.util.List;

public class Assignment extends Post{

    //TODO: This class and UserAssignment

    public Assignment(String title, String content, List<String> attachedFiles) {
        super(title, content, attachedFiles);
    }
}
