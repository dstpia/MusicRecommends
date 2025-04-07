package com.ragnarock.musicrecommends.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.ragnarock.musicrecommends.controllers..*(..))")
    public void logBeforeController(JoinPoint joinPoint) {
        log.info("Entering method: {} with arguments: {}",
                joinPoint.getSignature(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "execution(* com.ragnarock.musicrecommends.controllers..*(..))",
            returning = "result")
    public void logAfterController(JoinPoint joinPoint, Object result) {
        log.info("Exiting method: {} with result: {}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(pointcut = "execution(* com.ragnarock.musicrecommends..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        log.error("Exception in method: {} with cause: {}", joinPoint.getSignature(),
                ex.getMessage());
    }
}
