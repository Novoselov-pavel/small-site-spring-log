package com.npn.spring.learning.logger.smallsite.loggers;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogAspect {

    /**
     * Логгирование входа в методы пакета model
     * @param point
     */
    public void beforeLog(JoinPoint point) {
        Signature signature = point.getSignature();
        Logger logger = LoggerFactory.getLogger(signature.getDeclaringType());
        logger.debug("Enter the method: {}, parameter: {}",signature, point.getArgs());
    }


}
