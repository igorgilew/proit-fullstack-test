package ru.proit.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class WebServiceLogger {

    private static Logger LOG = LoggerFactory.getLogger(WebServiceLogger.class);

    @Pointcut(value = "execution(public * ru.proit.service.OrganizationService.*(..))")
    public void organizationServiceMethod() {}

    @Pointcut(value = "execution(public * ru.proit.service.WorkerService.*(..))")
    public void workerServiceMethod() {}

    @Pointcut("@annotation(ru.proit.annotation.Loggable)")
    public void loggableMethod(){}

    @Around(value = "(organizationServiceMethod() || workerServiceMethod())  && loggableMethod()")
    public Object logWebServiceCall(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        String methodName = thisJoinPoint.getSignature().getName();
        Object[] methodArgs = thisJoinPoint.getArgs();

        LOG.info("Call method " + methodName + " with args " + Arrays.toString(methodArgs));

        Object result = thisJoinPoint.proceed();

        LOG.info("Method " + methodName + " returns " + result);

        return result;
    }



}
