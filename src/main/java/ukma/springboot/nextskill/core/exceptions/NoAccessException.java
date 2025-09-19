package ukma.springboot.nextskill.core.exceptions;

public class NoAccessException extends RuntimeException{
    public NoAccessException(String message) {
        super(message);
    }
}
