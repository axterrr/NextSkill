package ukma.springboot.nextskill.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ukma.springboot.nextskill.entities.FileUploadEntity;
import ukma.springboot.nextskill.entities.UserEntity;
import ukma.springboot.nextskill.interfaces.IFileUploadService;
import ukma.springboot.nextskill.model.FileUpload;
import ukma.springboot.nextskill.model.User;
import ukma.springboot.nextskill.model.mappers.FileUploadMapper;
import ukma.springboot.nextskill.model.mappers.UserMapper;
import ukma.springboot.nextskill.repositories.FileUploadRepository;
import ukma.springboot.nextskill.repositories.UserRepository;
import ukma.springboot.nextskill.security.FileStorageType;
import ukma.springboot.nextskill.security.FileType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocalFileUploadService implements IFileUploadService {

    private static final Logger logger = LoggerFactory.getLogger(LocalFileUploadService.class);
    private static final Marker marker = MarkerFactory.getMarker("LOCAL_FILE_UPLOAD");

    @Value("${file.storage.dir}")
    private String storageDir;

    private FileUploadRepository fileUploadRepository;
    private UserRepository userRepository;

    @Override
    public UUID upload(String path, MultipartFile file, UUID currentUser) {
        try {
            UUID newFileUploadUUID = UUID.randomUUID();
            String extension = getFileExtension(file);

            FileType fileType = FileType.fromExtension(extension);

            Path targetPath = saveFileToPath(file, path, newFileUploadUUID);

            Optional<UserEntity> tempUser = userRepository.findById(currentUser);
            if (tempUser.isEmpty()) {
                return null;
            }

            FileUpload fileUpload = new FileUpload(
                    newFileUploadUUID,
                    fileType,
                    FileStorageType.LOCAL,
                    targetPath.toString(),
                    UserMapper.toUser(tempUser.get()),
                    false,
                    extension.isEmpty() ? null : extension
            );

            fileUploadRepository.save(FileUploadMapper.toFileUploadEntity(fileUpload));
            return newFileUploadUUID;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public File get(UUID fileUploadUUID) {
        Optional<FileUploadEntity> entity = fileUploadRepository.findById(fileUploadUUID);
        if (entity.isEmpty()) return null;

        FileUpload fileUpload = FileUploadMapper.toFileUpload(entity.get());
        Path path = Paths.get(fileUpload.getPath());

        File file = path.toFile();
        if (!file.exists()) {
            return null;
        }

        return file;
    }

    @Override
    public FileUpload getUpload(UUID upload) {
        Optional<FileUploadEntity> entity = fileUploadRepository.findById(upload);
        return entity.map(FileUploadMapper::toFileUpload).orElse(null);
    }

    @Override
    public boolean update(UUID fileUploadUUID, MultipartFile file) {
        try {
            String extension = getFileExtension(file);
            FileType fileType = FileType.fromExtension(extension);

            Optional<FileUploadEntity> temp = fileUploadRepository.findById(fileUploadUUID);
            if (temp.isEmpty()) {
                return false;
            }

            FileUpload fileUpload = FileUploadMapper.toFileUpload(temp.get());
            Path existingFile = Paths.get(fileUpload.getPath());

            try {
                boolean isFileDeleted = Files.deleteIfExists(existingFile);
                if (isFileDeleted) {
                    logger.trace(marker, "File was deleted successfully. Path: {}", existingFile);
                } else {
                    logger.trace(marker, "An error occurred while deleting a file in update() or this file doesn't exist. Path: {}", existingFile);
                }
            } catch (IOException e) {
                logger.warn(marker, "An error occurred while deleting a file in update(). Path: {}. Error: {}", existingFile, e.getMessage());
                return false;
            }

            User fileOwner = fileUpload.getOwner();

            Path targetPath = saveFileToPath(file, fileOwner.getUuid().toString(), fileUploadUUID);

            fileUpload.setPath(targetPath.toString());
            fileUpload.setType(fileType);
            fileUpload.setExt(extension.isEmpty() ? null : extension);

            fileUploadRepository.save(FileUploadMapper.toFileUploadEntity(fileUpload));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean delete(UUID fileUploadUUID) {
        return false;
    }

    private String getFileExtension(MultipartFile file) {
        String name = file.getOriginalFilename();
        if (name == null) return "";
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf+1);
    }

    private Path saveFileToPath(MultipartFile file, String path, UUID newFileUploadUUID) throws IOException {
        String fileTypeDir = storageDir + File.separator + path + File.separator + newFileUploadUUID;
        File dir = new File(fileTypeDir);
        if (!dir.exists()) {
            boolean res = dir.mkdirs();
            if (!res) {
                logger.warn(marker, "Unable to create directories: {}", fileTypeDir);
            }
        }

        Path targetPath = Paths.get(fileTypeDir + File.separator + file.getOriginalFilename());
        file.transferTo(targetPath);

        return targetPath;
    }

    @Autowired
    public void setFileUploadRepository(FileUploadRepository fileUploadRepository) {
        this.fileUploadRepository = fileUploadRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
