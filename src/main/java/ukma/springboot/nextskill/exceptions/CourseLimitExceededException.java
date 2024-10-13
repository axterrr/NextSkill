package ukma.springboot.nextskill.exceptions;

public class CourseLimitExceededException extends RuntimeException {

    public CourseLimitExceededException(String message) {
        super(message);
    }
}
