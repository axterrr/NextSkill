package ukma.springboot.nextskill.exceptions;

public class NoAccessException extends RuntimeException{
    public NoAccessException(String message) {
        super(message);
    }
}
