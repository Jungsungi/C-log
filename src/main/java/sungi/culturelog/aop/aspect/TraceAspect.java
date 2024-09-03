package sungi.culturelog.aop.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class TraceAspect {

    @Around("@annotation(sungi.culturelog.aop.annotation.Trace)")
    public Object beFore(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        log.info("[Start] {}, request url : {}, {}", joinPoint.getSignature(), request.getRequestURI(), request.getMethod());
        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            return result;
        } catch (Throwable e) {
            log.error("[Error] {}", joinPoint.getSignature());
            throw new RuntimeException(e);
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("[End] {}, time = {}", joinPoint.getSignature(), (endTime - startTime));
        }

    }
}
