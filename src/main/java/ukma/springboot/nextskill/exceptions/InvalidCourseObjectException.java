package ukma.springboot.nextskill.exceptions;

public class InvalidCourseObjectException extends RuntimeException {
    public InvalidCourseObjectException(String message) {
        super(message);
    }
}
