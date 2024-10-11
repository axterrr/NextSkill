package ukma.springboot.nextskill.model.mappers;

import ukma.springboot.nextskill.entities.AssignmentEntity;
import ukma.springboot.nextskill.entities.FileUploadEntity;
import ukma.springboot.nextskill.model.Assignment;
import ukma.springboot.nextskill.model.FileUpload;

import java.util.List;

public class AssignmentMapper {

    private AssignmentMapper() {}

    public static Assignment toAssignment(AssignmentEntity assignmentEntity) {
        if (assignmentEntity == null) {
            return null;
        }

        List<FileUpload> attachedFiles = assignmentEntity.getAttachedFiles().stream()
                .map(FileUploadMapper::toFileUpload)
                .toList();

        Assignment assignment = new Assignment(
                assignmentEntity.getTitle(),
                assignmentEntity.getUuid(),
                assignmentEntity.getCreatedAt(),
                assignmentEntity.isHidden(),
                assignmentEntity.getOrder()
        );

        assignment.setDueTo(assignmentEntity.getDueTo());
        assignment.setGrade(assignmentEntity.getGrade());
        assignment.setAttachedFiles(attachedFiles);
        assignment.setContent(assignmentEntity.getContent());
        return assignment;
    }

    public static AssignmentEntity toAssignmentEntity(Assignment assignment) {
        if (assignment == null) {
            return null;
        }

        List<FileUploadEntity> attachedFiles = assignment.getAttachedFiles().stream()
                .map(FileUploadMapper::toFileUploadEntity)
                .toList();

        AssignmentEntity assignmentEntity = new AssignmentEntity(
                assignment.getUuid(),
                assignment.getTitle(),
                assignment.getOrder(),
                assignment.isHidden(),
                assignment.getCreatedAt()
        );

        assignmentEntity.setDueTo(assignment.getDueTo());
        assignmentEntity.setGrade(assignment.getGrade());
        assignmentEntity.setAttachedFiles(attachedFiles);
        assignmentEntity.setContent(assignment.getContent());
        return assignmentEntity;
    }
}
