package ukma.springboot.nextskill.model.pojo;

import ukma.springboot.nextskill.model.enums.FileType;

import java.util.UUID;

public class FileUpload {
    private UUID id;
    private FileType type;
    private String serverUrl;
    private User owner;
    private boolean isPublic;

    public FileUpload() {}

    public FileUpload(UUID id, FileType type, String serverUrl, User owner, boolean isPublic) {
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }


}
