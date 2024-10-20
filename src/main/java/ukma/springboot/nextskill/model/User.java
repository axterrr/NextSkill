package ukma.springboot.nextskill.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class User {
    private UUID uuid;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String avatarLink;
    private String description;
    private LocalDateTime createdAt;
    private boolean isDisabled;
    private String passwordHash;
    private Set<Role> roles;

    public User() {
        this.uuid = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }
}
