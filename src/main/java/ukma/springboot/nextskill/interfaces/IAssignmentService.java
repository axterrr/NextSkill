package ukma.springboot.nextskill.interfaces;

import ukma.springboot.nextskill.entities.AssignmentEntity;

import java.util.List;
import java.util.UUID;

public interface IAssignmentService {
    AssignmentEntity createAssignment(AssignmentEntity assignment);

    AssignmentEntity updateAssignment(UUID id, AssignmentEntity assignment);

    List<AssignmentEntity> getAllAssignments();
    AssignmentEntity getAssignmentById(UUID assignmentId);
    void deleteAssignment(UUID assignmentId);
    void gradeAssignment(UUID assignmentId, int grade);
}