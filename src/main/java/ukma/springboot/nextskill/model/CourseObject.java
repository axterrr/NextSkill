package ukma.springboot.nextskill.model;

import java.time.LocalDateTime;
import java.util.UUID;

class CourseObject {
    private String title;
    private final UUID uuid;
    private final LocalDateTime createdAt;
    private boolean isHidden;

    CourseObject(String title) {
        this.title = title;
        this.uuid = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.isHidden = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }
}
