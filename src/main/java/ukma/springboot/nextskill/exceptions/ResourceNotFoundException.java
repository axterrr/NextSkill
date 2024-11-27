package ukma.springboot.nextskill.exceptions;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, UUID id) {
        super(resource + " with id '" + id + "' was not found.");
    }

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " with id '" + id + "' was not found.");
    }
}
