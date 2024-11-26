package ukma.springboot.nextskill.exceptions;

public class UnknownUser extends RuntimeException {
    public UnknownUser(String message) {
        super(message);
    }
}
