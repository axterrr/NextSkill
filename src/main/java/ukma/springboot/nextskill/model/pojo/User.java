package ukma.springboot.nextskill.model.pojo;

import lombok.Data;
import ukma.springboot.nextskill.model.enums.UserRole;

import java.time.LocalDateTime;
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
    private UserRole role;

    public User() {
        this.uuid = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.isDisabled = false;
    }

    @Override
    public String toString() {
        return "User{" +
                "avatarLink='" + avatarLink + '\'' +
                ", uuid=" + uuid +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", userRole=" + role +
                ", isDisabled=" + isDisabled +
                '}';
    }
}
