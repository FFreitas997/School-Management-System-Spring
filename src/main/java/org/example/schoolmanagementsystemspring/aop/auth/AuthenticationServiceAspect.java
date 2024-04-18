package org.example.schoolmanagementsystemspring.aop.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

/**
 * @author FFreitas
 * @LinkedIn: <a href="https://www.linkedin.com/in/francisco-freitas-a289b91b3/">Francisco Freitas</a>
 * @Github: <a href="https://github.com/FFreitas997">FFreitas997</a>
 * @Project: School-Management-System-Spring
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthenticationServiceAspect {

    @Around("execution(* org.example.schoolmanagementsystemspring.authentication.service.AuthenticationService.*(..))")
    public Object aroundMeasureTimeAuthenticationService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Auth service method has just started with signature: {}", proceedingJoinPoint.getSignature().toShortString());

        log.info("The time measure will start soon ...");

        Instant start = Instant.now();

        Object result = null;

        try {
            result = proceedingJoinPoint.proceed();
        } catch (Exception e) {
            log.error("An exception has occurred: {}", e.getMessage());
        }

        Instant endTime = Instant.now();

        log.info("Method Finished the execution");
        log.info("Duration: {} seconds || {} milliseconds",
                Duration.between(start, endTime).toSeconds(),
                Duration.between(start, endTime).toMillis()
        );

        return result;
    }
}
