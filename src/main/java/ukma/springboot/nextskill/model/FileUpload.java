package ukma.springboot.nextskill.model;

import ukma.springboot.nextskill.security.FileStorageType;
import ukma.springboot.nextskill.security.FileType;

import java.util.UUID;

public class FileUpload {
    private UUID id;
    private FileType type;
    private String path;
    private FileStorageType storageType;
    private User owner;
    private boolean isPublic;
    private String ext;

    public FileUpload() {}

    public FileUpload(UUID id, FileType type, FileStorageType storageType, String path, User owner, boolean isPublic, String ext) {
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

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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
