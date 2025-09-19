package ukma.springboot.nextskill.core.exceptions;

public class MaxAttemptsException extends RuntimeException{
    public MaxAttemptsException() {
        super("You reached max attempts amount for this test");
    }
}
