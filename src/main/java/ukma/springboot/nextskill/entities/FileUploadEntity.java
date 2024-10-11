package ukma.springboot.nextskill.entities;

import jakarta.persistence.*;
import ukma.springboot.nextskill.security.FileType;

import java.util.UUID;

@Entity
@Table(name = "file_uploads")
public class FileUploadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false)
    private FileType type;

    @Column(name = "server_url", nullable = false)
    private String serverUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false, updatable = false)
    private UserEntity owner;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    protected FileUploadEntity() {}

    public FileUploadEntity(UUID id, FileType type, String serverUrl, UserEntity owner, boolean isPublic) {
        this.id = id;
        this.type = type;
        this.serverUrl = serverUrl;
        this.owner = owner;
        this.isPublic = isPublic;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
