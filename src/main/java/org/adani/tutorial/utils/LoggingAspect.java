package org.adani.tutorial.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* org.adani.tutorial..*..*(..))")
    protected void onEntry(JoinPoint jp) {
        String args = getJointPointArgs(jp);
        String actionMessage = "ENTER: [" + jp.getSignature().getDeclaringTypeName() + "::" + jp.getSignature().getName() + "( " + args + " )]";
        LOGGER.info(actionMessage);
    }

    @After("execution(* org.adani.tutorial..*..*(..))")
    protected void onExit(JoinPoint jp) {
        String args = getJointPointArgs(jp);
        String actionMessage = "EXIT: [" + jp.getSignature().getDeclaringTypeName() + "::" + jp.getSignature().getName() + "( " + args + " )]";
        LOGGER.info(actionMessage);
    }


    @AfterThrowing(pointcut = ("execution(* org.adani.tutorial..*..*(..))"), throwing = "ex")
    protected void onExceptionThrown(JoinPoint jp, Throwable ex) {
        String fault = "EXCEPTION [\n" + ex.getMessage() + "\n]";
        LOGGER.info(fault);
        String args = getJointPointArgs(jp);
        String actionMessage = fault + "\nEXIT: [" + jp.getSignature().getDeclaringTypeName() + "::" + jp.getSignature().getName() + "( " + args + " )]";
        LOGGER.info(actionMessage);
    }


    private String getJointPointArgs(JoinPoint jp) {
        String args = "[";
        for (Object o: jp.getArgs())
            args += o.toString() + ", ";

        return args.length() > 1 ? args.substring(0, args.length() - 2) + "]" : "]";
    }

}
