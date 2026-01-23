package com.dimiya.studentinquiry.common.aop;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApiExecutionTimeAspect {

    private static final Logger log = LoggerFactory.getLogger(ApiExecutionTimeAspect.class);

    // Logs all methods inside any *Controller class under your base package
    @Around("within(com.dimiya.studentinquiry..*Controller)")
    public Object logApiExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        long startNs = System.nanoTime();

        try {
            return pjp.proceed();
        } finally {
            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

            String className = pjp.getSignature().getDeclaringTypeName();
            String methodName = pjp.getSignature().getName();

            // Keep args logging small (avoid huge payloads)
            String args = Arrays.stream(pjp.getArgs())
                    .map(a -> a == null ? "null" : a.getClass().getSimpleName())
                    .reduce((a, b) -> a + "," + b)
                    .orElse("");

            log.info("API_EXEC_TIME {}.{}({}) took {} ms", className, methodName, args, tookMs);
        }
    }
}
