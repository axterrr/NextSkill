package ukma.springboot.nextskill.services;

import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.entities.CourseEntity;
import ukma.springboot.nextskill.entities.CourseSectionEntity;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.interfaces.ICourseSectionService;
import ukma.springboot.nextskill.model.CourseSection;
import ukma.springboot.nextskill.model.mappers.CourseSectionMapper;
import ukma.springboot.nextskill.repositories.CourseRepository;
import ukma.springboot.nextskill.repositories.CourseSectionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class CourseSectionService implements ICourseSectionService {

    private final CourseSectionRepository courseSectionRepository;
    private final CourseRepository courseRepository;
    public CourseSectionService(CourseSectionRepository courseSectionRepository, CourseRepository courseRepository) {
        this.courseSectionRepository = courseSectionRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public CourseSection getCourseSection(UUID id) {
        Optional<CourseSectionEntity> result = courseSectionRepository.findById(id);
        if (result.isEmpty()) throw new ResourceNotFoundException("CourseSection", id.toString());
        return CourseSectionMapper.toCourseSection(result.get());
    }

    @Override
    public List<CourseSection> getAllCourseSections() {
        return courseSectionRepository.findAll().stream().map(CourseSectionMapper::toCourseSection).toList();
    }

    @Override
    public CourseSection createCourseSection(CourseSection section) {
        checkCourseExisting(section);
        CourseSectionEntity courseSectionEntity = CourseSectionMapper.toCourseSectionEntity(section);
        CourseSectionEntity savedEntity = courseSectionRepository.save(courseSectionEntity);
        return CourseSectionMapper.toCourseSection(savedEntity);
    }

    @Override
    public CourseSection updateCourseSection(UUID id, CourseSection updatedCourseSection) {
        Optional<CourseSectionEntity> existingCourseSection = courseSectionRepository.findById(id);
        if (existingCourseSection.isEmpty()) throw new ResourceNotFoundException("CourseSection", id.toString());
        checkCourseExisting(updatedCourseSection);
        updatedCourseSection.setUuid(existingCourseSection.get().getUuid());
        CourseSectionEntity result = courseSectionRepository.save(CourseSectionMapper.toCourseSectionEntity(updatedCourseSection));
        return CourseSectionMapper.toCourseSection(result);
    }

    @Override
    public void deleteCourseSection(UUID id) {
        Optional<CourseSectionEntity> result = courseSectionRepository.findById(id);
        if (result.isEmpty()) throw new ResourceNotFoundException("CourseSection", id.toString());
        courseSectionRepository.deleteById(id);
    }


    private void checkCourseExisting(CourseSection section) {
        Optional<CourseEntity> course = courseRepository.findById(section.getCourse().getUuid());
        if (course.isEmpty()) {
            throw new ResourceNotFoundException("Course", section.getCourse().getUuid().toString());
        }
    }
}
