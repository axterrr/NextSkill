package ukma.springboot.nextskill.advices;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ukma.springboot.nextskill.advices.annotations.RateLimited;
import ukma.springboot.nextskill.exceptions.RateLimitExceededException;

import java.util.*;

@Component
@Aspect
public class RateLimiter {

    private final Map<String, List<Long>> userCalls = new HashMap<>();

    @Around("@annotation(rateLimited)")
    public Object rateLimit(ProceedingJoinPoint joinPoint, RateLimited rateLimited) throws Throwable {

        String username = getCurrentUsername();
        if (username == null) {
            throw new IllegalStateException("User is not authenticated");
        }

        int limit = rateLimited.limit();
        long timeWindowMillis = rateLimited.timeWindowMillis();

        List<Long> calls = userCalls.computeIfAbsent(username, k -> new ArrayList<>());
        long currentTime = System.currentTimeMillis();
        calls.removeIf(timestamp -> currentTime - timestamp > timeWindowMillis);

        if (calls.size() >= limit) {
            throw new RateLimitExceededException(username);
        }

        calls.add(currentTime);
        return joinPoint.proceed();
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            return authentication.getName();
        }
        return null;
    }
}
