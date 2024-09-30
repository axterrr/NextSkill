package ukma.springboot.nextskill.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, String id) {
        super(resource + " with id '" + id + "' was not found");
    }
}
