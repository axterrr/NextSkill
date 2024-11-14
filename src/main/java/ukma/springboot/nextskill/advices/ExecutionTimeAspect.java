package ukma.springboot.nextskill.advices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ukma.springboot.nextskill.advices.annotations.LogExecutionTime;

@Component
@Aspect
public class ExecutionTimeAspect {

    private static final Logger logger = LogManager.getLogger(ExecutionTimeAspect.class);

    @Around("@annotation(annotation)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, LogExecutionTime annotation) throws Throwable {
        long start = System.currentTimeMillis();
        String label = "executed";

        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            label = "failed";
            throw e;
        } finally {
            long executionTime = System.currentTimeMillis() - start;
            String methodName = joinPoint.getSignature().toString();
            if (executionTime > annotation.threshold()) {
                if (annotation.errorIfExceeds()) {
                    logger.error("Method {} " + label + " in {} ms", methodName, executionTime);
                } else {
                    logger.warn("Method {} " + label + " in {} ms", methodName, executionTime);
                }
            } else {
                logger.info("Method {} " + label + " in {} ms", methodName, executionTime);
            }
        }
    }
}
