package ukma.springboot.nextskill.interfaces;

import org.springframework.web.multipart.MultipartFile;
import ukma.springboot.nextskill.model.FileUpload;

import java.io.File;
import java.util.UUID;

public interface IFileUploadService {
    UUID upload(String path, MultipartFile file, UUID currentUser);
    File get(UUID fileUploadUUID);
    boolean update(UUID fileUploadUUID, MultipartFile file);
    boolean delete(UUID fileUploadUUID);
    FileUpload getUpload(UUID upload);
}
