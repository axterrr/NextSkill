package ukma.springboot.nextskill.model.mappers;

import ukma.springboot.nextskill.entities.FileUploadEntity;
import ukma.springboot.nextskill.model.FileUpload;

public class FileUploadMapper {

    private FileUploadMapper() {}

    public static FileUpload toFileUpload(FileUploadEntity fileUploadEntity) {
        return new FileUpload(
                fileUploadEntity.getId(),
                fileUploadEntity.getType(),
                fileUploadEntity.getServerUrl(),
                UserMapper.toUser(fileUploadEntity.getOwner()),
                fileUploadEntity.isPublic()
        );
    }

    public static FileUploadEntity toFileUploadEntity(FileUpload fileUpload) {
        return new FileUploadEntity(
                fileUpload.getId(),
                fileUpload.getType(),
                fileUpload.getServerUrl(),
                UserMapper.toUserEntity(fileUpload.getOwner()),
                fileUpload.isPublic()
        );
    }
}
