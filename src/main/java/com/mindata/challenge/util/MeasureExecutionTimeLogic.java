package com.mindata.challenge.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class MeasureExecutionTimeLogic {

    @Around("@annotation(com.mindata.challenge.util.MeasureExecutionTime)")
    public Object measureExecutionTime(ProceedingJoinPoint point) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object object = point.proceed();
        stopWatch.stop();
        log.info(String.format("[MEASUREMENT] {Method: %s} - Execution time: %s ms",
                point.getSignature().getName(),
                stopWatch.getTotalTimeMillis()));
        return object;
    }
}
