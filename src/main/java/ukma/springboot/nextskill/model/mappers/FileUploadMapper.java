package ukma.springboot.nextskill.model.mappers;

import ukma.springboot.nextskill.entities.FileUploadEntity;
import ukma.springboot.nextskill.model.FileUpload;

public class FileUploadMapper {

    private FileUploadMapper() {}

    public static FileUpload toFileUpload(FileUploadEntity fileUploadEntity) {
        return new FileUpload(
                fileUploadEntity.getId(),
                fileUploadEntity.getType(),
                fileUploadEntity.getStorageType(),
                fileUploadEntity.getPath(),
                UserMapper.toUser(fileUploadEntity.getOwner()),
                fileUploadEntity.isPublic(),
                fileUploadEntity.getExt()
        );
    }

    public static FileUploadEntity toFileUploadEntity(FileUpload fileUpload) {
        return new FileUploadEntity(
                fileUpload.getId(),
                fileUpload.getType(),
                fileUpload.getStorageType(),
                fileUpload.getPath(),
                UserMapper.toUserEntity(fileUpload.getOwner()),
                fileUpload.isPublic(),
                fileUpload.getExt()
        );
    }
}
