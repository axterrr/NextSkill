package ukma.springboot.nextskill.advices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ukma.springboot.nextskill.exceptions.*;

import java.util.List;

@Component
@Aspect
public class ApplicationExceptionHandler {

    private static final Logger logger = LogManager.getLogger(ApplicationExceptionHandler.class);

    @Around("execution(* ukma.springboot.nextskill.controllers.*.*(..))")
    public Object handleErrors(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (MethodArgumentNotValidException e) {
            List<String> errors = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            logger.error("Validation error: {}", errors);
            ErrorResponse errorResponse = new ErrorResponse(errors);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            logger.error("Resource not found: {}", e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(List.of(e.getMessage()));
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (DuplicateUniqueFieldException e) {
            logger.error("Duplicate unique field exception: {}", e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(List.of(e.getMessage()));
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (CourseLimitExceededException e) {
            logger.error("Course limit exceeded: {}", e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(List.of(e.getMessage()));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (AccessDeniedException e) {
            logger.warn("Access denied: {}", e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(List.of("You do not have permission to access this resource."));
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        } catch (RateLimitExceededException e) {
            logger.warn("Access denied: {}", e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(List.of(e.getMessage()));
            return new ResponseEntity<>(errorResponse, HttpStatus.TOO_MANY_REQUESTS);
        } catch (IllegalStateException e) {
            logger.warn("Illegal state exception: {}", e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(List.of(e.getMessage()));
            return new ResponseEntity<>(errorResponse, HttpStatus.TOO_MANY_REQUESTS);
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage(), e);
            ErrorResponse errorResponse = new ErrorResponse(List.of("An unexpected error occurred. Please try again later."));
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
