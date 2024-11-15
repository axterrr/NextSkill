package ukma.springboot.nextskill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ukma.springboot.nextskill.entities.UserEntity;
import ukma.springboot.nextskill.interfaces.IFileUploadService;
import ukma.springboot.nextskill.model.FileUpload;
import ukma.springboot.nextskill.model.User;
import ukma.springboot.nextskill.model.mappers.UserMapper;
import ukma.springboot.nextskill.security.FileType;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/api/file")
public class FileUploadController {

    private IFileUploadService fileUploadService;

    @PostMapping("upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) return ResponseEntity.badRequest().body("No file provided.");

        UserEntity tempUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = UserMapper.toUser(tempUser);

        String path = currentUser.getUuid().toString();
        UUID res = fileUploadService.upload(path, file, currentUser.getUuid());
        if (res == null) {
            return ResponseEntity.internalServerError().body("An error occurred while uploading your file. Sorry :(");
        }

        return ResponseEntity.ok().body(res.toString());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable UUID id) {
        UserEntity tempUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = UserMapper.toUser(tempUser);

        FileUpload fileUpload = fileUploadService.getUpload(id);
        if (fileUpload == null) return ResponseEntity.notFound().build();

        if (!fileUpload.isPublic() && !currentUser.getUuid().equals(fileUpload.getOwner().getUuid()))
            return ResponseEntity.status(403).build();

        File file = fileUploadService.get(id);
        if (file == null) return ResponseEntity.internalServerError().build();
        MediaType contentType = FileType.getContentType(fileUpload.getExt());
        String contentDisposition = "attachment; filename=\"" + file.getName() + "\"";
        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .contentType(contentType)
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @GetMapping("clearcache")
    @CacheEvict(cacheNames = {"files"}, cacheManager = "ttlCacheManager", allEntries = true)
    public ResponseEntity<String> clearCache() {
        return ResponseEntity.ok().body("Cleared successfully!");
    }

    @Autowired
    public void setFileUploadService(IFileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }
}
