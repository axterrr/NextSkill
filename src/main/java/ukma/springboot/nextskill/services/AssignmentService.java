package ukma.springboot.nextskill.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.entities.AssignmentEntity;
import ukma.springboot.nextskill.interfaces.IAssignmentService;
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
    public AssignmentEntity createAssignment(AssignmentEntity assignment) {
        return assignmentRepository.save(assignment);
    }

    @Override
    public AssignmentEntity updateAssignment(UUID id, AssignmentEntity assignment) {
        Optional<AssignmentEntity> existingAssignment = assignmentRepository.findById(id);
        if (existingAssignment.isPresent()) {
            AssignmentEntity updatedAssignment = existingAssignment.get();

            updatedAssignment.setTitle(assignment.getTitle());
            updatedAssignment.setContent(assignment.getContent());

            if (assignment.getAttachedFiles() != null) {
                updatedAssignment.setAttachedFiles(assignment.getAttachedFiles());
            }

            if (assignment.getDueTo() != null) {
                updatedAssignment.setDueTo(assignment.getDueTo());
            }

            return assignmentRepository.save(updatedAssignment);
        } else {
            throw new IllegalArgumentException("Assignment not found with id: " + id);
        }
    }
    @Override
    public List<AssignmentEntity> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    @Override
    public AssignmentEntity getAssignmentById(UUID assignmentId) {
        return assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found with id: " + assignmentId));
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
