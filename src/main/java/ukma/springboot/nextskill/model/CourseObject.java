package ukma.springboot.nextskill.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class CourseObject {
    private String title;
    private final UUID uuid;
    private final LocalDateTime createdAt;
    private boolean isHidden = false;
    private int order = -1;

    CourseObject(String title) {
        this.title = title;
        this.uuid = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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
