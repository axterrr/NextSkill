//TODO: This class and UserAssignment

package ukma.springboot.nextskill.model;

import java.time.LocalDateTime;
import java.util.List;

public class Assignment extends Post{

    private LocalDateTime dueTo;

    public Assignment(String title, String content, List<String> attachedFiles) {
        super(title, content, attachedFiles);
        this.dueTo = LocalDateTime.now().plusDays(1);
    }

    public LocalDateTime getDueTo() {
        return dueTo;
    }

    public void setDueTo(LocalDateTime dueTo) {
        this.dueTo = dueTo;
    }
}
