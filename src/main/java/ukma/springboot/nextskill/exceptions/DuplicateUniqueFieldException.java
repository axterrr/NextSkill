package ukma.springboot.nextskill.exceptions;

public class DuplicateUniqueFieldException extends RuntimeException {
    public DuplicateUniqueFieldException(String owner, String field, String value) {
        super(owner + " with " + field + " '" + value + "' already exists");
    }
}
