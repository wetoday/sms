package sms.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

@Aspect // not @Component => not component-scan
public class LoggingAspect {

    private final Logger logger;

    @Autowired
    private HttpServletRequest request;

    public LoggingAspect() {
        this.logger = LoggerFactory.getLogger(LoggingAspect.class);
    }

    // for testability
    LoggingAspect(Logger logger, HttpServletRequest request) {
        this.logger = logger;
        this.request = request;
    }

    @Before("PointCutDefinition.handlerMethod()")
    public void loggingRequestMapping(JoinPoint joinPoint) {
        String requestMappingInfo = String.format(
                "Request arrived - [%s][%S] ===> [%s]",
                request.getRequestURL(), request.getMethod(), getMethodName(joinPoint));

        logger.info(requestMappingInfo);
    }

    @Before(value = "PointCutDefinition.exceptionHandler(ex)")
    public void loggingExceptionOccurred(Exception ex) {
        String requestMappingInfo = String.format(
                "Exception occurred - [%s] ===> [%s: \"%s\"]",
                request.getRequestURL(), ex.getClass(), ex.getMessage());

        logger.error(requestMappingInfo);
    }

    private String getMethodName(JoinPoint joinPoint) {
        if (joinPoint == null || joinPoint.getSignature() == null)
            return "Method Name Parsing Error";

        Signature method = joinPoint.getSignature();
        return String.format("%s.%s()", method.getDeclaringTypeName(), method.getName());
    }
}