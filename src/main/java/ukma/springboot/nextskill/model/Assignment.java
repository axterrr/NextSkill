//TODO: This class and UserAssignment

package ukma.springboot.nextskill.model;

import java.util.List;

public class Assignment extends Post{

    public Assignment(String title, String content, List<String> attachedFiles) {
        super(title, content, attachedFiles);
    }
}
