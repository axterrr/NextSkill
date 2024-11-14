package ukma.springboot.nextskill.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ukma.springboot.nextskill.utils.AbstractCSVConvertable;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends AbstractCSVConvertable {
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
        this.isDisabled = false;
        this.roles = new HashSet<>();
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
                ", userRole=" + roles.toString() +
                ", isDisabled=" + isDisabled +
                '}';
    }

    @Override
    public String getHeading() {
        return "UUID,Username,Name,Surname,Email,Phone,AvatarLink,Description,CreatedAt,IsDisabled,Roles";
    }

    @Override
    public String toSCV() {
        String rolesString = String.join(";", roles.stream().map(Role::toString).toArray(String[]::new));

        return String.format("%s,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%b,\"%s\"",
                uuid,
                username,
                name,
                surname,
                email,
                phone,
                avatarLink,
                description,
                createdAt,
                isDisabled,
                rolesString);
    }
}
