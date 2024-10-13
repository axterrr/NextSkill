package ukma.springboot.nextskill.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CourseObjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    @Column(name = "title", nullable = false)
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_hidden", nullable = false)
    private boolean isHidden = false;

    @Column(name = "order_index", nullable = false)
    private int order = -1;

    protected CourseObjectEntity(String title) {
        this.title = title;
        this.uuid = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }

    public CourseObjectEntity(UUID uuid, String title, int order, boolean isHidden, LocalDateTime createdAt) {
        this.uuid = uuid;
        this.title = title;
        this.order = order;
        this.isHidden = isHidden;
        this.createdAt = createdAt;
    }

    public CourseObjectEntity() {
        this.title = "New Object";
        this.uuid = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
