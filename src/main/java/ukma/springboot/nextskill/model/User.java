package ukma.springboot.nextskill.model;

import ukma.springboot.nextskill.security.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {
    private final UUID uuid;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String avatarLink;
    private String description;
    private final LocalDateTime createdAt;
    private UserRole userRole;
    private boolean isDisabled;

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public User() {
        this.uuid = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.userRole = UserRole.STUDENT;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid=" + uuid +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", avatarLink='" + avatarLink + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", userRole=" + userRole +
                ", isDisabled=" + isDisabled +
                '}';
    }
}
