package com.npn.spring.learning.logger.smallsite.loggers;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private static final String ERROR_FORM_NAME = "ErrorPage";

    @Pointcut("execution(public * com.npn.spring.learning.logger.smallsite.models.*.*(..))")
    public void anyModelMethod() {}

    @Pointcut("execution(public * com.npn.spring.learning.logger.smallsite.controllers.*.*(..)) && " +
            "!execution(public String com.npn.spring.learning.logger.smallsite.controllers.MainPageController.*.*(..)) && " +
            "!execution(* set*(..)) && " +
            "!execution(* *Json*(..))")
    public void controllerAround() {}

    /**
     * Логгирование входа в методы пакета model
     * @param point JoinPoint
     */
    @Before("anyModelMethod()")
    public void beforeLog(JoinPoint point) {
        Signature signature = point.getSignature();
        Logger logger = LoggerFactory.getLogger(signature.getDeclaringType());
        logger.debug("Enter the method: {}, parameter: {}",signature, point.getArgs());
    }

    /**
     * Логгирование и перехват ошибок
     *
     * @param joinPoint ProceedingJoinPoint
     * @return выход метода или страница ErrorPage в качестве ошибки
     */
    @Around("controllerAround()")
    public Object around(ProceedingJoinPoint joinPoint) {
        try {
            Object procced = joinPoint.proceed();
            return procced;
        } catch (Throwable e) {
            Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
            logger.error(e.getMessage(),e);
            return ERROR_FORM_NAME;
        }
    }


}
