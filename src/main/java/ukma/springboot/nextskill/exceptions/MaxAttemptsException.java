package ukma.springboot.nextskill.exceptions;

public class MaxAttemptsException extends RuntimeException{
    public MaxAttemptsException() {
        super("You reached max attempts amount for this test");
    }
}
