package ukma.springboot.nextskill.model;

import lombok.Data;

@Data
public class Role {
    private int id;
    private String title;

    public Role() {}

    public Role(int id, String title) {
        this.id = id;
        this.title = title;
    }
}
