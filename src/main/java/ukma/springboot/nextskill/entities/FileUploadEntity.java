package ukma.springboot.nextskill.entities;

import jakarta.persistence.*;
import ukma.springboot.nextskill.security.FileStorageType;
import ukma.springboot.nextskill.security.FileType;

import java.util.UUID;

@Entity
@Table(name = "file_uploads")
public class FileUploadEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false)
    private FileType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_storage_type", nullable = false)
    private FileStorageType storageType;

    @Column(name = "path", nullable = false)
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false, updatable = false)
    private UserEntity owner;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    @Column(name = "ext")
    private String ext;

    protected FileUploadEntity() {}

    public FileUploadEntity(UUID id, FileType type, FileStorageType storageType, String path, UserEntity owner, boolean isPublic, String ext) {
        this.id = id;
        this.type = type;
        this.storageType = storageType;
        this.path = path;
        this.owner = owner;
        this.isPublic = isPublic;
        this.ext = ext;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public UUID getId() {
        return id;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public UserEntity getOwner() {
        return owner;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FileStorageType getStorageType() {
        return storageType;
    }

    public void setStorageType(FileStorageType storageType) {
        this.storageType = storageType;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }
}
