package ukma.springboot.nextskill.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.model.entities.AssignmentEntity;
import ukma.springboot.nextskill.model.entities.FileUploadEntity;
import ukma.springboot.nextskill.services.interfaces.IAssignmentService;
import ukma.springboot.nextskill.model.pojo.Assignment;
import ukma.springboot.nextskill.model.mappers.AssignmentMapper;
import ukma.springboot.nextskill.model.mappers.FileUploadMapper;
import ukma.springboot.nextskill.repositories.AssignmentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AssignmentService implements IAssignmentService {

    private final AssignmentRepository assignmentRepository;

    @Autowired
    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }
    @Override
    public Assignment createAssignment(Assignment assignment) {
        AssignmentEntity assignmentEntity = AssignmentMapper.toAssignmentEntity(assignment);
        AssignmentEntity savedEntity = assignmentRepository.save(assignmentEntity);
        return AssignmentMapper.toAssignment(savedEntity);
    }

    @Override
    public Assignment updateAssignment(UUID id, Assignment assignment) {
        Optional<AssignmentEntity> existingAssignment = assignmentRepository.findById(id);

        if (existingAssignment.isPresent()) {
            AssignmentEntity updatedAssignment = existingAssignment.get();

            updatedAssignment.setTitle(assignment.getTitle());
            updatedAssignment.setContent(assignment.getContent());

            if (assignment.getAttachedFiles() != null) {
                List<FileUploadEntity> fileUploadEntities = assignment.getAttachedFiles()
                        .stream().map(FileUploadMapper::toFileUploadEntity).toList();
                updatedAssignment.setAttachedFiles(fileUploadEntities);
            }

            if (assignment.getDueTo() != null) {
                updatedAssignment.setDueTo(assignment.getDueTo());
            }

            return AssignmentMapper.toAssignment(assignmentRepository.save(updatedAssignment));
        } else {
            throw new IllegalArgumentException("Assignment not found with id: " + id);
        }
    }
    @Override
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll().stream().map(AssignmentMapper::toAssignment).toList();
    }

    @Override
    public Assignment getAssignmentById(UUID assignmentId) {
        Optional<AssignmentEntity> assignmentEntity = assignmentRepository.findById(assignmentId);
        if(assignmentEntity.isEmpty()) throw new IllegalArgumentException("Assignment not found with id: " + assignmentId);

        return AssignmentMapper.toAssignment(assignmentEntity.get());
    }

    @Override
    public void deleteAssignment(UUID assignmentId) {
        assignmentRepository.deleteById(assignmentId);
    }

    @Override
    public void gradeAssignment(UUID assignmentId, int grade) {
        AssignmentEntity assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found with id: " + assignmentId));
        assignment.setGrade(grade);
        assignmentRepository.save(assignment);
    }
}
