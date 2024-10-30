package ukma.springboot.nextskill.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ukma.springboot.nextskill.model.dto.CourseObjectDto;
import ukma.springboot.nextskill.model.entities.CourseObjectEntity;
import ukma.springboot.nextskill.exceptions.InvalidCourseObjectException;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.repositories.CourseObjectRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseObjectService {

    private final CourseObjectRepository courseObjectRepository;


    public CourseObjectService(CourseObjectRepository courseObjectRepository) {
        this.courseObjectRepository = courseObjectRepository;
    }

    public CourseObjectDto createCourseObject(CourseObjectDto courseObjectDto) {
        validateCourseObject(courseObjectDto);

        CourseObjectEntity entity = mapDtoToEntity(courseObjectDto);
        CourseObjectEntity savedEntity = courseObjectRepository.save(entity);

        return mapEntityToDto(savedEntity);
    }

    public List<CourseObjectDto> getAllCourseObjects() {
        List<CourseObjectEntity> entities = courseObjectRepository.findAll();

        return entities.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }

    public CourseObjectDto getCourseObject(UUID uuid) {
        Optional<CourseObjectEntity> optionalEntity = courseObjectRepository.findById(uuid);
        if (optionalEntity.isEmpty()) {
            throw new ResourceNotFoundException("CourseObject with UUID ", uuid.toString() );
        }
        return mapEntityToDto(optionalEntity.get());
    }

    public CourseObjectDto updateCourseObject(UUID uuid, CourseObjectDto courseObjectDto) {
        validateCourseObject(courseObjectDto);

        Optional<CourseObjectEntity> optionalEntity = courseObjectRepository.findById(uuid);
        if (optionalEntity.isEmpty()) {
            throw new ResourceNotFoundException("CourseObject with UUID ", uuid.toString() );
        }

        CourseObjectEntity entityToUpdate = optionalEntity.get();
        entityToUpdate.setTitle(courseObjectDto.getTitle());
        entityToUpdate.setOrder(courseObjectDto.getOrder());
        entityToUpdate.setHidden(courseObjectDto.isHidden());

        CourseObjectEntity updatedEntity = courseObjectRepository.save(entityToUpdate);

        return mapEntityToDto(updatedEntity);
    }

    public void deleteCourseObject(UUID uuid) {
        Optional<CourseObjectEntity> optionalEntity = courseObjectRepository.findById(uuid);
        if (optionalEntity.isEmpty()) {
            throw new ResourceNotFoundException("CourseObject with UUID ", uuid.toString() );
        }

        courseObjectRepository.deleteById(uuid);
    }

    private void validateCourseObject(CourseObjectDto dto) {
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new InvalidCourseObjectException("Title cannot be blank");
        }
    }

    private CourseObjectEntity mapDtoToEntity(CourseObjectDto dto) {
        return new CourseObjectEntity(
                UUID.randomUUID(),
                dto.getTitle(),
                dto.getOrder(),
                dto.isHidden(),
                LocalDateTime.now()
        );
    }

    private CourseObjectDto mapEntityToDto(CourseObjectEntity entity) {
        CourseObjectDto dto = new CourseObjectDto();
        dto.setTitle(entity.getTitle());
        dto.setOrder(entity.getOrder());
        dto.setHidden(entity.isHidden());
        return dto;
    }
}
