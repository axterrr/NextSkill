package ukma.springboot.nextskill.model;

import ukma.springboot.nextskill.entities.UserEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

public class Course {
    private final UUID uuid;
    private String name;
    private String description;
    private LocalDate creationDate;
    private User teacher;

    public Course() {
        this.uuid = UUID.randomUUID();
        creationDate = LocalDate.now();
    }

    public Course(UUID uuid, LocalDate createdAt) {
        this.uuid = uuid;
        this.creationDate = createdAt;
    }

    private List<UserEntity> users;

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }
}

