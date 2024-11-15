package ukma.springboot.nextskill.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.entities.FileUploadEntity;

import java.util.UUID;

public interface FileUploadRepository extends JpaRepository<FileUploadEntity, UUID> {
}
