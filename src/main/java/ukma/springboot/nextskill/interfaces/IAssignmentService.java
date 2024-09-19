package ukma.springboot.nextskill.interfaces;

import ukma.springboot.nextskill.model.Assignment;

import java.util.List;
import java.util.UUID;

public interface IAssignmentService {
    Assignment createAssignment(Assignment assignment);
    Assignment updateAssignment(UUID id, Assignment assignment);
    List<Assignment> getAllAssignments();
    Assignment getAssignmentById(UUID assignmentId);
    void deleteAssignment(UUID assignmentId);
    void gradeAssignment(UUID assignmentId, int grade);
}