package ukma.springboot.nextskill.common.exceptions;

public class NoAccessException extends RuntimeException{
    public NoAccessException(String message) {
        super(message);
    }
}
